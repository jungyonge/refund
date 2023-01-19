package app.api.refund.business.userservice.infrastructure.domain.role;


import app.api.refund.business.userservice.domain.security.Role;
import app.api.refund.business.userservice.domain.security.RoleName;
import app.api.refund.business.userservice.domain.security.RoleRepository;
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
