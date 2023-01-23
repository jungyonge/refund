package app.api.refund.business.refundservice.application.refund;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import app.api.refund.api.refund.response.RefundResponse;
import app.api.refund.business.scrapservice.domain.scrap.CalculatedTax;
import app.api.refund.business.scrapservice.domain.scrap.CalculatedTaxRepository;
import app.api.refund.business.scrapservice.domain.scrap.IncomeDeduction;
import app.api.refund.business.scrapservice.domain.scrap.IncomeDeductionRepository;
import app.api.refund.business.scrapservice.domain.scrap.Pay;
import app.api.refund.business.scrapservice.domain.scrap.PayRepository;
import app.api.refund.business.scrapservice.domain.scrap.Scrap;
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
    @Mock
    private CalculatedTaxRepository calculatedTaxRepository;
    @Mock
    private IncomeDeductionRepository incomeDeductionRepository;
    @Mock
    private PayRepository payRepository;
    @InjectMocks
    private RefundHandler refundHandler;

    @Test
    @DisplayName("스크랩 데이터 기준으로 환급금 조회성공")
    void getRefundData_Success() {
        //given
        doReturn(Optional.of(User.create("홍길동", "", "홍길동", "", "", "")))
                .when(userRepository)
                .getUserByIdAndUserId(1, "홍길동");
        doReturn(Scrap.create(1L, "", "", "", "", "", null, null))
                .when(scrapRepository)
                .getScrapByUserId(null);
        doReturn(Pay.create(null, null, null, 60000000.0, null, null,
                null, null, null, null, null))
                .when(payRepository)
                .getPayByUserIdAndScrapId(null, null);
        doReturn(IncomeDeduction.create(null, null,
                100000.0, 200000.0, 150000.0, 4400000.0, 6000000.0))
                .when(incomeDeductionRepository)
                .getIncomeDeductionByUserIdAndScrapId(null, null);
        doReturn(CalculatedTax.create(null, null, 3000000.0))
                .when(calculatedTaxRepository)
                .getCalculatedTaxByUserIdAndScrapId(null, null);
        // when
        RefundResponse response = refundHandler.makeRefundData(1, "홍길동");
        // then
        assertEquals("900,000", response.getRetirementPensionTaxCredit());
        assertEquals("0", response.getDeterminedTaxAmount());
    }


    @Test
    @DisplayName("스크랩데이터가 없을시 에러 발생")
    void getRefundData_NoScrapData_ExceptionThrown() {
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