package app.api.refund.business.scrapservice.infrastrucure.scrap;

import app.api.refund.business.scrapservice.domain.scrap.IncomeDeduction;
import app.api.refund.business.scrapservice.domain.scrap.IncomeDeductionRepository;
import org.springframework.stereotype.Repository;

@Repository
public class IncomeDeductionRepositoryImpl implements IncomeDeductionRepository {

    private final IncomeDeductionJpaRepository incomeDeductionJpaRepository;

    public IncomeDeductionRepositoryImpl(IncomeDeductionJpaRepository incomeDeductionJpaRepository) {
        this.incomeDeductionJpaRepository = incomeDeductionJpaRepository;
    }

    @Override
    public IncomeDeduction save(IncomeDeduction incomeDeduction) {
        return incomeDeductionJpaRepository.save(incomeDeduction);
    }

    @Override
    public IncomeDeduction getIncomeDeductionByUserIdAndScrapId(Long userId, Long scrapId) {
        return incomeDeductionJpaRepository.findTop1ByUserIdAndScrapId(userId, scrapId);
    }
}
