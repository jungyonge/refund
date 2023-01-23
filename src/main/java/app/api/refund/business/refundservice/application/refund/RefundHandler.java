package app.api.refund.business.refundservice.application.refund;

import app.api.refund.business.refundservice.domain.RefundDomainValidationMessage;
import app.api.refund.business.refundservice.domain.refund.Refund;
import app.api.refund.business.refundservice.domain.refund.RefundRepository;
import app.api.refund.business.scrapservice.domain.scrap.CalculatedTax;
import app.api.refund.business.scrapservice.domain.scrap.CalculatedTaxRepository;
import app.api.refund.business.scrapservice.domain.scrap.IncomeDeduction;
import app.api.refund.business.scrapservice.domain.scrap.IncomeDeductionRepository;
import app.api.refund.business.scrapservice.domain.scrap.Pay;
import app.api.refund.business.scrapservice.domain.scrap.PayRepository;
import app.api.refund.business.scrapservice.domain.scrap.Scrap;
import app.api.refund.business.scrapservice.domain.scrap.ScrapRepository;
import app.api.refund.business.userservice.domain.UserDomainValidationMessage;
import app.api.refund.business.userservice.domain.user.UserRepository;
import app.api.refund.domain.DomainValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RefundHandler {

    private final UserRepository userRepository;

    private final ScrapRepository scrapRepository;

    private final CalculatedTaxRepository calculatedTaxRepository;

    private final IncomeDeductionRepository incomeDeductionRepository;

    private final PayRepository payRepository;

    private final RefundRepository refundRepository;

    public RefundHandler(UserRepository userRepository, ScrapRepository scrapRepository,
            CalculatedTaxRepository calculatedTaxRepository,
            IncomeDeductionRepository incomeDeductionRepository, PayRepository payRepository,
            RefundRepository refundRepository) {
        this.userRepository = userRepository;
        this.scrapRepository = scrapRepository;
        this.calculatedTaxRepository = calculatedTaxRepository;
        this.incomeDeductionRepository = incomeDeductionRepository;
        this.payRepository = payRepository;
        this.refundRepository = refundRepository;
    }

    @Transactional
    public Refund makeRefundData(long id, String userId) {

        return userRepository.getUserByIdAndUserId(id, userId).map(user -> {

            Scrap scrap = scrapRepository.getScrapByUserId(user.getId());
            if (scrap == null) {
                throw new DomainValidationException(RefundDomainValidationMessage.NOT_FOUND_SCRAP);
            }

            Pay pay = payRepository.getPayByUserIdAndScrapId(user.getId(), scrap.getId());
            IncomeDeduction incomeDeduction = incomeDeductionRepository.getIncomeDeductionByUserIdAndScrapId(
                    user.getId(), scrap.getId());
            CalculatedTax calculatedTax = calculatedTaxRepository.getCalculatedTaxByUserIdAndScrapId(
                    user.getId(), scrap.getId());

            // 결정세액 계산
            double determinedTaxAmount = calculateDeterminedTaxAmount(pay, incomeDeduction,
                    calculatedTax);
            //퇴직연금세액공제금액 = 퇴직연금 납입금액 * 0.15
            double retirementPensionTaxDeduction = incomeDeduction.getRetirementPension() * 0.15;

            Refund refund = Refund.create(id, determinedTaxAmount, retirementPensionTaxDeduction);
            refundRepository.save(refund);

            return refund;

        }).orElseThrow(
                () -> new DomainValidationException(UserDomainValidationMessage.USER_NOT_FOUND));
    }

    // 결정세액 계산
    // 결정세액 = 산출세액 - 근로소득세액공제금액 - 특별세액공제금액 - 표준세액공제금액 - 퇴직연금세액공제금액
    private double calculateDeterminedTaxAmount(Pay pay, IncomeDeduction incomeDeduction,
            CalculatedTax calculatedTax) {
        // 근로소득세액공제금액 = 산출세액 * 0.55
        double employmentIncomeTax = calculatedTax.getCalculatedTaxAmount() * 0.55;

        //보험료공제금액 = 보험료납임금액 * 12%
        double insurancePremiumDeduction = incomeDeduction.getPremium() * 0.12;
        //의료비공제금액 = (의료비납임금액 - 총급여 * 3%) * 15%
        double medicalExpenseDeduction =
                (incomeDeduction.getMedicalExpenses() - (pay.getTotalPayment() * 0.03)) * 0.15;
        medicalExpenseDeduction = (medicalExpenseDeduction < 0) ? 0 : medicalExpenseDeduction;
        //교육비공제금액 = 교육비납입금액 * 15%
        double educationExpensesDeduction = incomeDeduction.getEducationExpenses() * 0.15;
        //기부금공제금액 = 기부금납입금액 * 15%
        double donationDeduction = incomeDeduction.getDonations() * 0.15;

        //특별세액공제금액 = 보험료공제금액, 의료비공제금액, 교육비공제금액, 기부금공제금액
        double specialTaxDeduction =
                insurancePremiumDeduction + medicalExpenseDeduction + educationExpensesDeduction
                        + donationDeduction;

        //표준세액공제금액
        //특별세액공제금액의 합 < 130,000 이면 표준세액공제금액 = 130,000원
        //특별세액공제금액의 합 >= 130,000 이면 표준세액공제금액 = 0
        double standardTaxDeduction = (specialTaxDeduction < 130000) ? 130000 : 0;

        //퇴직연금세액공제금액 = 퇴직연금 납입금액 * 0.15
        double retirementPensionTaxDeduction = incomeDeduction.getRetirementPension() * 0.15;

        double determinedTaxAmount =
                calculatedTax.getCalculatedTaxAmount() - employmentIncomeTax - specialTaxDeduction
                        - standardTaxDeduction - retirementPensionTaxDeduction;

        return (determinedTaxAmount < 0) ? 0 : determinedTaxAmount;
    }

    @Transactional(readOnly = true)
    public Refund getRefundData(long id) {
        return refundRepository.getRefundByUserId(id);
    }
}
