package app.api.refund.business.userservice.domain;


import app.api.refund.domain.ExplainableMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserDomainValidationMessage implements ExplainableMessage {

    USER_ID_ALREADY_EXIST(2_0001, "이미 가입된 아이디 입니다."),
    INVALID_PASSWORD(2_0002, "비밀번호 조건이 올바르지 않습니다."),
    WHITE_LIST_NOT_FOUND(2_0003, "가입가능한 회원이 아닙니다. 관리자에게 문의해주세요."),
    ROLE_ALREADY_EXIST(2_0004, "이미 존재하는 권한입니다."),
    NICKNAME_ALREADY_EXIST(2_0005, "이미 존재하는 닉네임입니다."),
    ROLE_DOES_NOT_EXIST(2_0006, "권한이 존재하지 않습니다."),
    USER_NOT_FOUND(2_0006, "사용자가 존재하지 않습니다."),
    USER_NOT_FOUND_OR_PASSWORD_WRONG(2_0007, "사용자가 존재하지 않거나 비밀번호가 틀렸습니다."),
    ALREADY_SING_UP_REG_NO(2_0008, "이미 가입된 주민등록 번호입니다.")

    ;

    private final int code;
    private final String message;
    private final HttpStatus status;

    UserDomainValidationMessage(int code, String message) {
        this.code = code;
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
    }

    public int getStatus() {
        return status.value();
    }
}
