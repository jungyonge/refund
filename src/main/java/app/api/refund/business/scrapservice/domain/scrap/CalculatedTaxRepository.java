package app.api.refund.business.scrapservice.domain.scrap;

public interface CalculatedTaxRepository {

    CalculatedTax save(CalculatedTax calculatedTax);

    CalculatedTax getCalculatedTaxByUserId(Long userId);
}
