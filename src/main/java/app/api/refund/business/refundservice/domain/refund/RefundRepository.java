package app.api.refund.business.refundservice.domain.refund;

public interface RefundRepository {
    Refund save(Refund refund);

    Refund getRefundByUserId(long userId);
}
