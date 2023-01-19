package app.api.refund.business.scrapservice.infrastrucure.scrap;

import app.api.refund.business.scrapservice.domain.scrap.CalculatedTax;
import org.springframework.data.repository.CrudRepository;

public interface CalculatedTaxJpaRepository extends CrudRepository<CalculatedTax, Long> {

    CalculatedTax findTop1ByUserId(Long userId);
}
