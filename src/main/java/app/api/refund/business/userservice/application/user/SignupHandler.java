package app.api.refund.business.userservice.application.user;

import app.api.refund.business.userservice.domain.UserDomainValidationMessage;
import app.api.refund.business.userservice.domain.role.RoleName;
import app.api.refund.business.userservice.domain.role.RoleRepository;
import app.api.refund.business.userservice.domain.user.User;
import app.api.refund.business.userservice.domain.user.UserRepository;
import app.api.refund.business.userservice.domain.user.WhiteList;
import app.api.refund.business.userservice.domain.user.WhiteListRepository;
import app.api.refund.domain.DomainValidationException;
import app.api.refund.util.Aes256Util;
import app.api.refund.util.Sha256Util;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignupHandler {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final WhiteListRepository whiteListRepository;
    private final PasswordEncoder passwordEncoder;
    private final Aes256Util aes256Util;
    private final Sha256Util sha256Util;

    public SignupHandler(UserRepository userRepository, RoleRepository roleRepository,
            WhiteListRepository whiteListRepository, PasswordEncoder passwordEncoder,
            Aes256Util aes256Util, Sha256Util sha256Util) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.whiteListRepository = whiteListRepository;
        this.passwordEncoder = passwordEncoder;
        this.aes256Util = aes256Util;
        this.sha256Util = sha256Util;
    }

    @Transactional
    public User signupUser(String userId, String password, String name, String regNo){
        WhiteList whiteList = whiteListRepository.getWhiteListByName(name);
        if(whiteList == null || !passwordEncoder.matches(regNo, whiteList.getRegNo())){
            throw new DomainValidationException(UserDomainValidationMessage.WHITE_LIST_NOT_FOUND);
        }

        if (userRepository.getUserByUserId(userId).isPresent()) {
            throw new DomainValidationException(UserDomainValidationMessage.USER_ID_ALREADY_EXIST);
        }

        if (userRepository.getUserByRegNo(sha256Util.Sha256(regNo)).isPresent()) {
            throw new DomainValidationException(UserDomainValidationMessage.ALREADY_SING_UP_REG_NO);
        }

        String aes256Iv = aes256Util.generateIv();
        String regNoAes256 = aes256Util.encryptAES256(regNo, aes256Iv);
        var user = User.create(userId, passwordEncoder.encode(password), name, sha256Util.Sha256(regNo), regNoAes256, aes256Iv);

        var normalUser = roleRepository.getRoleByName(RoleName.ROLE_NORMAL_USER)
                .orElseThrow(() -> new DomainValidationException(
                        UserDomainValidationMessage.ROLE_DOES_NOT_EXIST));
        user.addRole(normalUser);

        return userRepository.save(user);
    }
}
