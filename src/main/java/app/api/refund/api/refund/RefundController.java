package app.api.refund.api.refund;

import app.api.refund.api.refund.response.RefundResponse;
import app.api.refund.business.refundservice.application.refund.RefundHandler;
import app.api.refund.config.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RefundController {

    private final RefundHandler refundHandler;

    public RefundController(RefundHandler refundHandler) {
        this.refundHandler = refundHandler;
    }

    @GetMapping("/szs/refund")
    public ResponseEntity<RefundResponse> getRefundData(@AuthenticationPrincipal CustomUserDetails userDetails){
        RefundResponse refundResponse = refundHandler.getRefundData(userDetails.getUserId());
        return ResponseEntity.ok(refundResponse);
    }
}
