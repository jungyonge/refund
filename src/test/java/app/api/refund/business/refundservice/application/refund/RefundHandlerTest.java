package app.api.refund.business.refundservice.application.refund;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import app.api.refund.business.scrapservice.domain.scrap.ScrapRepository;
import app.api.refund.business.userservice.domain.user.User;
import app.api.refund.business.userservice.domain.user.UserRepository;
import app.api.refund.domain.DomainValidationException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RefundHandlerTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ScrapRepository scrapRepository;
    @InjectMocks
    private RefundHandler refundHandler;

    @Test
    @DisplayName("스크랩데이터가 없을시 에러 발생")
    void makeRefundData_NoScrapData_ExceptionThrown() {
        //given
        doReturn(Optional.of(User.create("홍길동", "", "홍길동", "", "", "")))
                .when(userRepository)
                .getUserByIdAndUserId(1, "홍길동");
        // when
        DomainValidationException e = assertThrows(DomainValidationException.class,
                () -> refundHandler.makeRefundData(1, "홍길동"));
        // then
        assertEquals("스크랩 데이터가 없습니다. 스크랩데이터 요청 먼저 해주세요.", e.getMessage());
    }
}