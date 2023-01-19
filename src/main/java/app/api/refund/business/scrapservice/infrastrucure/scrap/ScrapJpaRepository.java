package app.api.refund.business.scrapservice.infrastrucure.scrap;

import app.api.refund.business.scrapservice.domain.scrap.CalculatedTax;
import app.api.refund.business.scrapservice.domain.scrap.Scrap;
import app.api.refund.business.scrapservice.domain.scrap.ScrapRepository;
import org.springframework.data.repository.CrudRepository;

public interface ScrapJpaRepository extends CrudRepository<Scrap, Long> {

    Scrap findTop1ByUserId(Long userId);
}
