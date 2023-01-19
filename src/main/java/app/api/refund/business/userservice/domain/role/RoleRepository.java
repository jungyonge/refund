package app.api.refund.business.userservice.domain.role;

import java.util.Optional;

public interface RoleRepository {

    Optional<Role> getRoleByName(RoleName name);
}
