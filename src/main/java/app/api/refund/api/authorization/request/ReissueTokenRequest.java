package app.api.refund.api.authorization.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "JWT access token 재발급 요청 request")
@Getter
@AllArgsConstructor
public class ReissueTokenRequest {

    @Schema(description = "access token")
    @NotNull
    private String access_token;

    @Schema(description = "refresh token")
    @NotNull
    private String refresh_token;

}
