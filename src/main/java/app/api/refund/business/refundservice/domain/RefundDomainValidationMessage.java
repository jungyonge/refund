package app.api.refund.business.refundservice.domain;


import app.api.refund.domain.ExplainableMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RefundDomainValidationMessage implements ExplainableMessage {

    NOT_FOUND_SCRAP(4_0001, "스크랩 데이터가 없습니다. 스크랩데이터 요청 먼저 해주세요."),


    ;

    private final int code;
    private final String message;
    private final HttpStatus status;

    RefundDomainValidationMessage(int code, String message) {
        this.code = code;
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
    }

    public int getStatus() {
        return status.value();
    }
}
