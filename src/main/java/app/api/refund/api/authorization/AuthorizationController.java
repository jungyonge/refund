package app.api.refund.api.authorization;


import app.api.refund.api.authorization.request.LoginRequest;
import app.api.refund.api.authorization.request.NewAccessTokenRequest;
import app.api.refund.api.authorization.response.TokenDto;
import app.api.refund.business.authorizationservice.application.authorization.AuthorizationHandler;
import app.api.refund.config.jwt.TokenProvider;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizationController {

    private final AuthorizationHandler authorizationHandler;
    private final TokenProvider tokenProvider;

    public AuthorizationController(AuthorizationHandler authorizationHandler,
            TokenProvider tokenProvider) {
        this.authorizationHandler = authorizationHandler;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/szs/login")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginRequest loginRequest) {

        TokenDto tokenDto = authorizationHandler.loginUser(loginRequest.getUserId(), loginRequest.getPassword());

        return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    }

    @PostMapping("/szs/jwt/reissue")
    public ResponseEntity<TokenDto> reissueAccessToken(@RequestBody NewAccessTokenRequest newAccessTokenRequest) {

        String newAccessToken = tokenProvider.reissueAccessToken(
                newAccessTokenRequest.getAccess_token(), newAccessTokenRequest.getRefresh_token());
        TokenDto tokenDto = new TokenDto(newAccessToken, newAccessTokenRequest.getRefresh_token());

        return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    }

}
