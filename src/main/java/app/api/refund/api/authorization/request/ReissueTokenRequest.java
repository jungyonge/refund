package app.api.refund.api.authorization.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReissueTokenRequest {
    @NotNull
    private String access_token;

    @NotNull
    private String refresh_token;

}
