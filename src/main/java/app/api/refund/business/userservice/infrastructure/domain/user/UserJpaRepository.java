package app.api.refund.business.userservice.infrastructure.domain.user;

import app.api.refund.business.userservice.domain.user.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserJpaRepository extends CrudRepository<User, Long> {

    Optional<User> findUserByIdAndUserId(long id, String userId);
    Optional<User> findUserByUserId(String userId);

}
