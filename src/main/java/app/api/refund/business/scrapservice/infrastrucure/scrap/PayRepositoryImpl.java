package app.api.refund.business.scrapservice.infrastrucure.scrap;

import app.api.refund.business.scrapservice.domain.scrap.Pay;
import app.api.refund.business.scrapservice.domain.scrap.PayRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PayRepositoryImpl implements PayRepository {

    private final PayJpaRepository payJpaRepository;

    public PayRepositoryImpl(PayJpaRepository payJpaRepository) {
        this.payJpaRepository = payJpaRepository;
    }

    @Override
    public Pay save(Pay pay) {
        return payJpaRepository.save(pay);
    }

    @Override
    public Pay getPayByUserIdAndScrapId(Long userId, Long scrapId) {
        return payJpaRepository.findTop1ByUserIdAndScrapId(userId, scrapId);
    }
}
