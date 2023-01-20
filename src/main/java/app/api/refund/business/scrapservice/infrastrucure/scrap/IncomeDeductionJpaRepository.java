package app.api.refund.business.scrapservice.infrastrucure.scrap;

import app.api.refund.business.scrapservice.domain.scrap.IncomeDeduction;
import org.springframework.data.repository.CrudRepository;

public interface IncomeDeductionJpaRepository extends CrudRepository<IncomeDeduction, Long> {

    IncomeDeduction findTop1ByUserIdAndScrapId(Long userId, Long scrapId);

}
