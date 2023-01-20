package app.api.refund.api.authorization.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenDto {

    private String access_token;
    private String refresh_token;

}
