package app.api.refund.api.user.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "회원가입 성공 response")
@Getter
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class UserDto {

    @Schema(description = "회원가입 ID")
    @NotNull
    private String userId;

    @Schema(description = "이름")
    @NotNull
    private String name;

}
