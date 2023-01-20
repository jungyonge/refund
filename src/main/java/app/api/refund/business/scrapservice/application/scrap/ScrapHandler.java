package app.api.refund.business.scrapservice.application.scrap;


import app.api.refund.business.scrapservice.domain.ScrapDomainValidationMessage;
import app.api.refund.business.scrapservice.domain.scrap.CalculatedTax;
import app.api.refund.business.scrapservice.domain.scrap.CalculatedTaxRepository;
import app.api.refund.business.scrapservice.domain.scrap.IncomeDeduction;
import app.api.refund.business.scrapservice.domain.scrap.IncomeDeductionRepository;
import app.api.refund.business.scrapservice.domain.scrap.Pay;
import app.api.refund.business.scrapservice.domain.scrap.PayRepository;
import app.api.refund.business.scrapservice.domain.scrap.Scrap;
import app.api.refund.business.scrapservice.domain.scrap.ScrapRepository;
import app.api.refund.business.scrapservice.domain.szs.SzsScrapData;
import app.api.refund.business.scrapservice.domain.szs.SzsScrapData.IncomeDeductionData;
import app.api.refund.business.scrapservice.domain.szs.SzsScrapData.JsonList;
import app.api.refund.business.scrapservice.domain.szs.SzsScrapData.PayData;
import app.api.refund.business.scrapservice.domain.szs.SzsScrapData.ScrapData;
import app.api.refund.business.scrapservice.domain.szs.SzsScrapService;
import app.api.refund.business.userservice.domain.UserDomainValidationMessage;
import app.api.refund.business.userservice.domain.user.User;
import app.api.refund.business.userservice.domain.user.UserRepository;
import app.api.refund.domain.DomainValidationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScrapHandler {

    private final SzsScrapService szsScrapService;

    private final UserRepository userRepository;

    private final ScrapRepository scrapRepository;

    private final CalculatedTaxRepository calculatedTaxRepository;

    private final IncomeDeductionRepository incomeDeductionRepository;

    private final PayRepository payRepository;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");


    public ScrapHandler(SzsScrapService szsScrapService, UserRepository userRepository,
            ScrapRepository scrapRepository, CalculatedTaxRepository calculatedTaxRepository,
            IncomeDeductionRepository incomeDeductionRepository, PayRepository payRepository) {
        this.szsScrapService = szsScrapService;
        this.userRepository = userRepository;
        this.scrapRepository = scrapRepository;
        this.calculatedTaxRepository = calculatedTaxRepository;
        this.incomeDeductionRepository = incomeDeductionRepository;
        this.payRepository = payRepository;
    }

    @Transactional
    public boolean getScrapData(String userId, String accessToken) {
        return userRepository.getUserByUserId(userId).map(user -> {
            SzsScrapData szsScrapData = szsScrapService.getScrapData(user.getName(),
                    user.getRegNo(), accessToken);
            if(szsScrapData.getStatus().equals("success")){
                ScrapData scrapData = szsScrapData.getData();

                Scrap scrap = Scrap.create(user.getId(), scrapData.getAppVer(), scrapData.getErrMsg(),
                        scrapData.getCompany(),
                        scrapData.getSvcCd(), scrapData.getHostNm(), scrapData.getWorkerResDt(),
                        scrapData.getWorkerReqDt());
                scrapRepository.save(scrap);

                JsonList jsonList = scrapData.getJsonList();

                //급여데이터 저장
                savePayData(jsonList, user, scrap);
                //소득공제데이터 저장
                saveIncomeDeductionData(jsonList, user, scrap);
                //산출세액데이터 저장
                saveCalculatedTaxData(jsonList, user, scrap);

                return true;
            }else {
                if(szsScrapData.getErrors().getCode().equals("-1")){
                    throw new DomainValidationException(ScrapDomainValidationMessage.INVALID_USER);
                }
            }
            return false;
        }).orElseThrow(() -> new DomainValidationException(UserDomainValidationMessage.USER_NOT_FOUND));
    }

    private void savePayData(JsonList jsonList, User user, Scrap scrap){
        for (PayData data : jsonList.getPayData()) {
            Pay pay = Pay.create(user.getId(), scrap.getId(), data.getIncomeDetails(),
                    Integer.valueOf(data.getTotalPayment().replaceAll(",", "")),
                    LocalDate.parse(data.getWorkStartDate(), formatter),
                    LocalDate.parse(data.getWorkEndDate(), formatter), data.getCompany(),
                    data.getName(), LocalDate.parse(data.getPaymentDate(), formatter),
                    data.getIncomeClassification(), data.getCompanyRegistrationNumber());
            payRepository.save(pay);
        }
    }

    private void saveIncomeDeductionData(JsonList jsonList, User user, Scrap scrap){
        int premium = 0, educationExpenses = 0, donations = 0, medicalExpenses = 0, retirementPension = 0;

        for (IncomeDeductionData data : jsonList.getIncomeDeductionData()) {
            switch (data.getIncomeClassification()) {
                case "보험료":
                    premium = Integer.parseInt(data.getAmount().replaceAll(",", ""));
                    break;
                case "교육비":
                    educationExpenses = Integer.parseInt(data.getAmount().replaceAll(",", ""));
                    break;
                case "기부금":
                    donations = Integer.parseInt(data.getAmount().replaceAll(",", ""));
                    break;
                case "의료비":
                    medicalExpenses = Integer.parseInt(data.getAmount().replaceAll(",", ""));
                    break;
                case "퇴직연금":
                    retirementPension = Integer.parseInt(
                            data.getTotalAmountOfPaid().replaceAll(",", ""));
                    break;
            }
        }

        IncomeDeduction incomeDeduction = IncomeDeduction.create(user.getId(), scrap.getId(),
                premium, educationExpenses, donations, medicalExpenses, retirementPension);
        incomeDeductionRepository.save(incomeDeduction);
    }

    private void saveCalculatedTaxData(JsonList jsonList, User user, Scrap scrap){
        CalculatedTax calculatedTax = CalculatedTax.create(user.getId(), scrap.getId(),
                Integer.valueOf(jsonList.getCalculatedTaxAmount().replaceAll(",", "")));

        calculatedTaxRepository.save(calculatedTax);
    }
}
