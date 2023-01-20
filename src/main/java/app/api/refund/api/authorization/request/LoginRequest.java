package app.api.refund.api.authorization.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {

    @NotNull
    private String userId;

    @NotNull
    private String password;
}
