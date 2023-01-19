package app.api.refund.business.userservice.infrastructure.role;


import app.api.refund.business.userservice.domain.role.Role;
import app.api.refund.business.userservice.domain.role.RoleName;
import app.api.refund.business.userservice.domain.role.RoleRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private final RoleJpaRepository roleJpaRepository;

    @Override
    public Optional<Role> getRoleByName(RoleName name) {
        return roleJpaRepository.findByName(name);
    }
}
