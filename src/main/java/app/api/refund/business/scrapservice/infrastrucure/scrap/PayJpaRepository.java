package app.api.refund.business.scrapservice.infrastrucure.scrap;

import app.api.refund.business.scrapservice.domain.scrap.CalculatedTax;
import app.api.refund.business.scrapservice.domain.scrap.Pay;
import org.springframework.data.repository.CrudRepository;

public interface PayJpaRepository extends CrudRepository<Pay, Long> {

    Pay findTop1ByUserId(Long userId);

}
