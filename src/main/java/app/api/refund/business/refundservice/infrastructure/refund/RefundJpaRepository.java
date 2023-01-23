package app.api.refund.business.refundservice.infrastructure.refund;

import app.api.refund.business.refundservice.domain.refund.Refund;
import org.springframework.data.repository.CrudRepository;

public interface RefundJpaRepository extends CrudRepository<Refund, Long> {

    Refund findTop1ByUserIdOrderByCreatedDesc(long userId);
}
