package app.api.refund.business.scrapservice.infrastrucure.scrap;

import app.api.refund.business.scrapservice.domain.scrap.Scrap;
import org.springframework.data.repository.CrudRepository;

public interface ScrapJpaRepository extends CrudRepository<Scrap, Long> {

    Scrap findTop1ByUserIdOrderByCreatedDesc(Long userId);
}
