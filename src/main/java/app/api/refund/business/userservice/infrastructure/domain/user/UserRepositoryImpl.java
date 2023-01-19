package app.api.refund.business.userservice.infrastructure.domain.user;

import app.api.refund.business.userservice.domain.user.User;
import app.api.refund.business.userservice.domain.user.UserRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public Optional<User> getUserByUserId(String userId) {
        return userJpaRepository.findUserByUserId(userId);
    }


}
