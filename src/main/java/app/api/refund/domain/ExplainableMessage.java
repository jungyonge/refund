package app.api.refund.domain;

public interface ExplainableMessage {

    int getCode();
    String getMessage();
    int getStatus();
}
