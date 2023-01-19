package app.api.refund.business.userservice.infrastructure.role;


import app.api.refund.business.userservice.domain.role.Role;
import app.api.refund.business.userservice.domain.role.RoleName;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RoleJpaRepository extends CrudRepository<Role, Integer> {

    Optional<Role> findByName(RoleName name);
}
