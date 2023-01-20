package app.api.refund.api.user;

import app.api.refund.api.user.request.SignupRequest;
import app.api.refund.api.user.response.UserDto;
import app.api.refund.business.userservice.application.user.SignupHandler;
import app.api.refund.config.security.CustomUserDetails;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final SignupHandler signupService;

    public UserController(SignupHandler signupService) {
        this.signupService = signupService;
    }

    @PostMapping("/szs/signup")
    public ResponseEntity<UserDto> signup(@Valid @RequestBody SignupRequest signupRequest) {

        var user = signupService.signupUser(signupRequest.getUserId(),
                signupRequest.getPassword(), signupRequest.getName(),
                signupRequest.getRegNo());

        return ResponseEntity.ok(new UserDto(user.getUserId(), user.getName()));
    }

    @GetMapping("/szs/me")
    @Secured({"ROLE_NORMAL_USER"})
    public ResponseEntity<UserDto> getProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(new UserDto(userDetails.getUserId(), userDetails.getName()));
    }
}
