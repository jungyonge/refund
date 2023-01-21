package app.api.refund.api.authorization.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "로그인 요청 request")
@Getter
@AllArgsConstructor
public class LoginRequest {

    @Schema(description = "회원가입 요청 ID", example = "honggildong")
    @NotNull
    private String userId;

    @Schema(description = "패스워드", example = "honggildong")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    private String password;
}
