package app.api.refund.business.scrapservice.infrastrucure.scrap;

import app.api.refund.business.scrapservice.domain.scrap.CalculatedTax;
import app.api.refund.business.scrapservice.domain.scrap.CalculatedTaxRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CalculatedTaxRepositoryImpl implements CalculatedTaxRepository {

    private final CalculatedTaxJpaRepository calculatedTaxJpaRepository;

    public CalculatedTaxRepositoryImpl(CalculatedTaxJpaRepository calculatedTaxJpaRepository) {
        this.calculatedTaxJpaRepository = calculatedTaxJpaRepository;
    }

    @Override
    public CalculatedTax save(CalculatedTax calculatedTax) {
        return calculatedTaxJpaRepository.save(calculatedTax);
    }

    @Override
    public CalculatedTax getCalculatedTaxByUserIdAndScrapId(Long userId, Long scrapId) {
        return calculatedTaxJpaRepository.findTop1ByUserIdAndScrapId(userId, scrapId);
    }
}
