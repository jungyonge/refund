package app.api.refund.business.userservice.application.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import app.api.refund.business.userservice.domain.role.RoleRepository;
import app.api.refund.business.userservice.domain.user.User;
import app.api.refund.business.userservice.domain.user.UserRepository;
import app.api.refund.business.userservice.domain.user.WhiteList;
import app.api.refund.business.userservice.domain.user.WhiteListRepository;
import app.api.refund.domain.DomainValidationException;
import app.api.refund.util.Aes256Util;
import app.api.refund.util.Sha256Util;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class SignupHandlerTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private WhiteListRepository whiteListRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private Aes256Util aes256Util;
    @Mock
    private Sha256Util sha256Util;
    @InjectMocks
    private SignupHandler signupHandler;


    @Test
    @DisplayName("화이트리스트에 없을시 가입 불가능")
    void signupUser_NoWhiteList_ExceptionThrown(){
        //given
        doReturn(new WhiteList()).when(whiteListRepository).getWhiteListByName("");
        // when
        DomainValidationException e = assertThrows(DomainValidationException.class, () -> signupHandler.signupUser("","","",""));
        // then
        assertEquals("가입가능한 회원이 아닙니다. 관리자에게 문의해주세요.", e.getMessage());
    }


    @Test
    @DisplayName("같은 ID가 있을시 가입 불가능")
    void signupUser_AlreadySignUpUserId_ExceptionThrown(){
        //given
        doReturn(Optional.of(User.create("","","","","",""))).when(userRepository).getUserByUserId("");
        doReturn(WhiteList.create("","")).when(whiteListRepository).getWhiteListByName("");
        doReturn(true).when(passwordEncoder).matches("","");
        // when
        DomainValidationException e = assertThrows(DomainValidationException.class, () -> signupHandler.signupUser("","","",""));
        // then
        assertEquals("이미 가입된 아이디 입니다.", e.getMessage());
    }

    @Test
    @DisplayName("같은 주민번호가 있을시 가입 불가능")
    void signupUser_AlreadySignUpRegNo_ExceptionThrown(){
        //given
        doReturn(Optional.empty()).when(userRepository).getUserByUserId("");
        doReturn(WhiteList.create("","")).when(whiteListRepository).getWhiteListByName("");
        doReturn(true).when(passwordEncoder).matches("","");
        doReturn(Optional.of(User.create("","","","","",""))).when(userRepository).getUserByRegNo("");
        doReturn("").when(sha256Util).Sha256("");
        // when
        DomainValidationException e = assertThrows(DomainValidationException.class, () -> signupHandler.signupUser("","","",""));
        // then
        assertEquals("이미 가입된 주민등록 번호입니다.", e.getMessage());
    }


}