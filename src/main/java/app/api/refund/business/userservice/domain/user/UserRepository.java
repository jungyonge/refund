package app.api.refund.business.userservice.domain.user;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> getUserByUserId(String userId);

    Optional<User> getUserByRegNo(String regNo);


}




