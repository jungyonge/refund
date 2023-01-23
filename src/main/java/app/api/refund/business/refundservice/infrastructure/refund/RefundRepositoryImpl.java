package app.api.refund.business.refundservice.infrastructure.refund;

import app.api.refund.business.refundservice.domain.refund.Refund;
import app.api.refund.business.refundservice.domain.refund.RefundRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RefundRepositoryImpl implements RefundRepository {

    private final RefundJpaRepository refundJpaRepository;

    public RefundRepositoryImpl(RefundJpaRepository refundJpaRepository) {
        this.refundJpaRepository = refundJpaRepository;
    }

    @Override
    public Refund save(Refund refund) {
        return refundJpaRepository.save(refund);
    }

    @Override
    public Refund getRefundByUserId(long userId) {
        return refundJpaRepository.findTop1ByUserIdOrderByCreatedDesc(userId);
    }
}
