package app.api.refund.business.userservice.application.user;

import app.api.refund.business.userservice.domain.UserDomainValidationMessage;
import app.api.refund.business.userservice.domain.role.RoleName;
import app.api.refund.business.userservice.domain.role.RoleRepository;
import app.api.refund.business.userservice.domain.user.User;
import app.api.refund.business.userservice.domain.user.UserRepository;
import app.api.refund.domain.DomainValidationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignupService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupService(UserRepository userRepository, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User signupUser(String userId, String password, String name, String regNo){
        var user = User.create(userId, passwordEncoder.encode(password), name, regNo);

        if (userRepository.getUserByUserId(userId).isPresent()) {
            throw new DomainValidationException(UserDomainValidationMessage.USER_ID_ALREADY_EXIST);
        }

        var normalUser = roleRepository.getRoleByName(RoleName.ROLE_NORMAL_USER)
                .orElseThrow(() -> new DomainValidationException(
                        UserDomainValidationMessage.ROLE_DOES_NOT_EXIST));
        user.addRole(normalUser);

        return userRepository.save(user);
    }
}
