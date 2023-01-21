package app.api.refund.api.authorization;


import app.api.refund.api.authorization.request.LoginRequest;
import app.api.refund.api.authorization.request.ReissueTokenRequest;
import app.api.refund.api.authorization.response.TokenDto;
import app.api.refund.business.authorizationservice.application.authorization.AuthorizationHandler;
import app.api.refund.config.jwt.TokenProvider;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authorization API", description = "인증관련 API")
@RestController
public class AuthorizationController {

    private final AuthorizationHandler authorizationHandler;
    private final TokenProvider tokenProvider;

    public AuthorizationController(AuthorizationHandler authorizationHandler,
            TokenProvider tokenProvider) {
        this.authorizationHandler = authorizationHandler;
        this.tokenProvider = tokenProvider;
    }

    @Tag(name = "Authorization API", description = "인증관련 API")
    @ApiOperation(
            value = "로그인 요청"
            , notes = "회원가입이 완료된 회원만 로그인 가능")
    @PostMapping("/szs/login")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginRequest loginRequest) {

        TokenDto tokenDto = authorizationHandler.loginUser(loginRequest.getUserId(), loginRequest.getPassword());

        return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    }

    @Tag(name = "Authorization API", description = "인증관련 API")
    @ApiOperation(
            value = "JWT 재발급 요청"
            , notes = "access, refresh 둘다 유효해야 재발급 가능")
    @PostMapping("/szs/jwt/reissue")
    public ResponseEntity<TokenDto> reissueAccessToken(@RequestBody ReissueTokenRequest newAccessTokenRequest) {

        String newAccessToken = tokenProvider.reissueAccessToken(
                newAccessTokenRequest.getAccess_token(), newAccessTokenRequest.getRefresh_token());
        TokenDto tokenDto = new TokenDto(newAccessToken, newAccessTokenRequest.getRefresh_token());

        return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    }

}
