package app.api.refund.api.refund;

import app.api.refund.api.refund.response.RefundResponse;
import app.api.refund.business.refundservice.application.refund.RefundHandler;
import app.api.refund.config.security.CustomUserDetails;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Tag(name = "Refund API", description = "환급금 조회관련 API")
@RestController
public class RefundController {

    private final RefundHandler refundHandler;

    public RefundController(RefundHandler refundHandler) {
        this.refundHandler = refundHandler;
    }

    @Tag(name = "Refund API", description = "환급금 조회관련 API")
    @ApiOperation(
            value = "환급금 조회 요청"
            , notes = "access token 유효해야 재발급 가능")
    @GetMapping("/szs/refund")
    public ResponseEntity<RefundResponse> getRefundData(
            @ApiIgnore @AuthenticationPrincipal CustomUserDetails userDetails){
        RefundResponse refundResponse = refundHandler.getRefundData(userDetails.getId(), userDetails.getUserId());
        return ResponseEntity.ok(refundResponse);
    }
}
