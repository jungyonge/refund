package app.api.refund.business.scrapservice.infrastrucure.scrap;

import app.api.refund.business.scrapservice.domain.scrap.Scrap;
import app.api.refund.business.scrapservice.domain.scrap.ScrapRepository;
import org.springframework.stereotype.Service;

@Service
public class ScrapRepositoryImpl implements ScrapRepository {

    private final ScrapJpaRepository scrapJpaRepository;

    public ScrapRepositoryImpl(ScrapJpaRepository scrapJpaRepository) {
        this.scrapJpaRepository = scrapJpaRepository;
    }

    @Override
    public Scrap save(Scrap scrap) {
        return scrapJpaRepository.save(scrap);
    }

    @Override
    public Scrap getScrapByUserId(Long userId) {
        return scrapJpaRepository.findTop1ByUserIdOrderByCreatedDesc(userId);
    }
}
