package app.api.refund.api.refund.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "환급금 조회 성공 response")
@Getter
@AllArgsConstructor
public class RefundResponse {

    @Schema(description = "이름", example = "홍길동")
    @JsonProperty("이름")
    public String name;

    @Schema(description = "결정세액", example = "100,000")
    @JsonProperty("결정세액")
    public String determinedTaxAmount;

    @Schema(description = "퇴직연금세액공제", example = "200,000")
    @JsonProperty("퇴직연금세액공제")
    public String retirementPensionTaxCredit;
}
