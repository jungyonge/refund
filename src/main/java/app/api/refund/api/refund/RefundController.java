package app.api.refund.api.refund;

import app.api.refund.api.refund.response.RefundResponse;
import app.api.refund.business.refundservice.application.refund.RefundHandler;
import app.api.refund.business.refundservice.domain.refund.Refund;
import app.api.refund.config.security.CustomUserDetails;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.text.DecimalFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Tag(name = "Refund API", description = "환급금 조회관련 API")
@RestController
public class RefundController {

    private final RefundHandler refundHandler;

    DecimalFormat decFormat = new DecimalFormat("###,###");

    public RefundController(RefundHandler refundHandler) {
        this.refundHandler = refundHandler;
    }

    @Tag(name = "Refund API", description = "환급금 조회관련 API")
    @ApiOperation(
            value = "환급금 조회 요청"
            , notes = "access token 유효해야 재발급 가능")
    @Secured({"ROLE_NORMAL_USER"})
    @GetMapping("/szs/refund")
    public ResponseEntity<RefundResponse> getRefundData(
            @ApiIgnore @AuthenticationPrincipal CustomUserDetails userDetails){
        Refund refund = refundHandler.makeRefundData(userDetails.getId(), userDetails.getUserId());
        return ResponseEntity.ok(new RefundResponse(userDetails.getName(), decFormat.format(refund.getDeterminedTaxAmount()), decFormat.format(refund.getRetirementPensionTaxCredit())));
    }
}
