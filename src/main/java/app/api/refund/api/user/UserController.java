package app.api.refund.api.user;

import app.api.refund.api.user.request.SignupRequest;
import app.api.refund.api.user.response.SignUpDto;
import app.api.refund.api.user.response.UserDto;
import app.api.refund.business.refundservice.application.refund.RefundHandler;
import app.api.refund.business.refundservice.domain.refund.Refund;
import app.api.refund.business.userservice.application.user.SignupHandler;
import app.api.refund.config.security.CustomUserDetails;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.text.DecimalFormat;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User API", description = "유저관련 API")
@RestController
public class UserController {

    private final SignupHandler signupService;

    private final RefundHandler refundHandler;

    DecimalFormat decFormat = new DecimalFormat("###,###");

    public UserController(SignupHandler signupService, RefundHandler refundHandler) {
        this.signupService = signupService;
        this.refundHandler = refundHandler;
    }

    @Tag(name = "User API", description = "유저관련 API")
    @ApiOperation(
            value = "회원가입 요청"
            , notes = "화이트리스트에 등록된 사람만 회원가입 가능")
    @PostMapping("/szs/signup")
    public ResponseEntity<SignUpDto> signup(@Valid @RequestBody SignupRequest signupRequest) {

        var user = signupService.signupUser(signupRequest.getUserId(),
                signupRequest.getPassword(), signupRequest.getName(),
                signupRequest.getRegNo());

        return ResponseEntity.ok(new SignUpDto(user.getUserId(), user.getName()));
    }

    @Tag(name = "User API", description = "유저관련 API")
    @ApiOperation(
            value = "본인정보 요청"
            , notes = "요청시 ID와 이름정보를 리턴합니다.")
    @GetMapping("/szs/me")
    @Secured({"ROLE_NORMAL_USER"})
    public ResponseEntity<UserDto> getProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Refund refund = refundHandler.getRefundData(userDetails.getId());
        if(refund != null){
            return ResponseEntity.ok(new UserDto(userDetails.getUserId(), userDetails.getName(),
                    decFormat.format(refund.getDeterminedTaxAmount()), decFormat.format(refund.getRetirementPensionTaxCredit())));
        }else {
            return ResponseEntity.ok(new UserDto(userDetails.getUserId(), userDetails.getName(),
                    null, null));
        }
    }
}
