package app.api.refund.business.userservice.infrastructure.domain.role;


import app.api.refund.business.userservice.domain.security.Role;
import app.api.refund.business.userservice.domain.security.RoleName;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RoleJpaRepository extends CrudRepository<Role, Integer> {

    Optional<Role> findByName(RoleName name);
}
