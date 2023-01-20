package app.api.refund.business.scrapservice.domain;


import app.api.refund.domain.ExplainableMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ScrapDomainValidationMessage implements ExplainableMessage {

    INVALID_USER(3_0001, "요청하신 값은 스크랩 가능유저가 아닙니다."),
    SCRAP_ERROR(3_0002,"스크랩 데이터를 불러오지 못하였습니다. 관리자에게 문의해주세요")

    ;

    private final int code;
    private final String message;
    private final HttpStatus status;

    ScrapDomainValidationMessage(int code, String message) {
        this.code = code;
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
    }

    public int getStatus() {
        return status.value();
    }
}
