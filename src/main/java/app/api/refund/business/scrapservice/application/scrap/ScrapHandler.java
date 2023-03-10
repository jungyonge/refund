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
import app.api.refund.util.Aes256Util;
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

    private final Aes256Util aes256Util;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");


    public ScrapHandler(SzsScrapService szsScrapService, UserRepository userRepository,
            ScrapRepository scrapRepository, CalculatedTaxRepository calculatedTaxRepository,
            IncomeDeductionRepository incomeDeductionRepository, PayRepository payRepository,
            Aes256Util aes256Util) {
        this.szsScrapService = szsScrapService;
        this.userRepository = userRepository;
        this.scrapRepository = scrapRepository;
        this.calculatedTaxRepository = calculatedTaxRepository;
        this.incomeDeductionRepository = incomeDeductionRepository;
        this.payRepository = payRepository;
        this.aes256Util = aes256Util;
    }

    @Transactional
    public boolean getScrapData(String userId, String accessToken) {
        return userRepository.getUserByUserId(userId).map(user -> {

            String originRegNo = aes256Util.decryptAES256(user.getRegNoAes256(), user.getAes256Iv());
            SzsScrapData szsScrapData = szsScrapService.getScrapData(user.getName(), originRegNo, accessToken);

            if(szsScrapData.getStatus().equals("success")){
                ScrapData scrapData = szsScrapData.getData();

                Scrap scrap = Scrap.create(user.getId(), scrapData.getAppVer(), scrapData.getErrMsg(),
                        scrapData.getCompany(),
                        scrapData.getSvcCd(), scrapData.getHostNm(), scrapData.getWorkerResDt(),
                        scrapData.getWorkerReqDt());
                scrapRepository.save(scrap);

                JsonList jsonList = scrapData.getJsonList();

                //??????????????? ??????
                savePayData(jsonList, user, scrap);
                //????????????????????? ??????
                saveIncomeDeductionData(jsonList, user, scrap);
                //????????????????????? ??????
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
                    Double.parseDouble(data.getTotalPayment().replaceAll(",", "")),
                    LocalDate.parse(data.getWorkStartDate(), formatter),
                    LocalDate.parse(data.getWorkEndDate(), formatter), data.getCompany(),
                    data.getName(), LocalDate.parse(data.getPaymentDate(), formatter),
                    data.getIncomeClassification(), data.getCompanyRegistrationNumber());
            payRepository.save(pay);
        }
    }

    private void saveIncomeDeductionData(JsonList jsonList, User user, Scrap scrap){
        double premium = 0, educationExpenses = 0, donations = 0, medicalExpenses = 0, retirementPension = 0;

        for (IncomeDeductionData data : jsonList.getIncomeDeductionData()) {
            switch (data.getIncomeClassification()) {
                case "?????????":
                    premium = Double.parseDouble(data.getAmount().replaceAll(",", ""));
                    break;
                case "?????????":
                    educationExpenses = Double.parseDouble(data.getAmount().replaceAll(",", ""));
                    break;
                case "?????????":
                    donations = Double.parseDouble(data.getAmount().replaceAll(",", ""));
                    break;
                case "?????????":
                    medicalExpenses = Double.parseDouble(data.getAmount().replaceAll(",", ""));
                    break;
                case "????????????":
                    retirementPension = Double.parseDouble(
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
                Double.parseDouble(jsonList.getCalculatedTaxAmount().replaceAll(",", "")));

        calculatedTaxRepository.save(calculatedTax);
    }
}
