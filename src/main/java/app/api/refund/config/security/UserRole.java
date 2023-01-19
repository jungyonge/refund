package app.api.refund.config.security;

import app.api.refund.business.userservice.domain.security.RoleName;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRole implements GrantedAuthority {

    private int id;

    private RoleName name;

    private LocalDateTime created;

    @Override
    public String getAuthority() {
        return name.getValue();
    }
}
