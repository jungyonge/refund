package app.api.refund.api.refund.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefundResponse {

    @JsonProperty("이름")
    public String name;

    @JsonProperty("결정세액")
    public String determinedTaxAmount;

    @JsonProperty("퇴직연금세액공제")
    public String retirementPensionTaxCredit;
}
