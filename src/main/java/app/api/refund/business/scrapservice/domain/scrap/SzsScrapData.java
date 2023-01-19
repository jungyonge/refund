package app.api.refund.business.scrapservice.domain.scrap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SzsScrapData {

    public String status;
    @JsonProperty("data")
    public ScrapData data;
    public Errors errors;

    @Getter
    public static class ScrapData {
        public JsonList jsonList;
        public String appVer;
        public String errMsg;
        public String company;
        public String svcCd;
        public String hostNm;
        public LocalDateTime workerResDt;
        public LocalDateTime workerReqDt;
    }

    @Getter
    public static class JsonList {

        @JsonProperty("급여")
        public ArrayList<Pay> pay;

        @JsonProperty("산출세액")
        public String calculatedTaxAmount;

        @JsonProperty("소득공제")
        public ArrayList<IncomeDeduction> incomeDeduction;
    }

    @Getter
    public static class Pay {

        @JsonProperty("소득내역")
        public String incomeDetails;

        @JsonProperty("총지급액")
        public String totalPayment;

        @JsonProperty("업무시작일")
        public String workStartDate;

        @JsonProperty("기업명")
        public String company;

        @JsonProperty("이름")
        public String name;

        @JsonProperty("지급일")
        public String paymentDate;

        @JsonProperty("업무종료일")
        public String workEndDate;

        @JsonProperty("주민등록번호")
        public String regNo;

        @JsonProperty("소득구분")
        public String incomeClassification;

        @JsonProperty("사업자등록번호")
        public String companyRegistrationNumber;
    }

    @Getter
    public static class IncomeDeduction {

        @JsonProperty("금액")
        public String amount;

        @JsonProperty("소득구분")
        public String incomeClassification;

        @JsonProperty("총납임금액")
        public String totalAmountOfPaid;
    }

    @Getter
    public static class Errors{
        public String code;
        public String message;
    }
}