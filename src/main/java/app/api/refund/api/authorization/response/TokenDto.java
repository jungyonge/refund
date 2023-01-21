package app.api.refund.api.authorization.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "로그인 성공 response")
@Getter
@AllArgsConstructor
public class TokenDto {

    @Schema(description = "access token")
    private String access_token;

    @Schema(description = "refresh token")
    private String refresh_token;

}
