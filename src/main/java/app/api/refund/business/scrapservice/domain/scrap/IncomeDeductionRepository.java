package app.api.refund.business.scrapservice.domain.scrap;

public interface IncomeDeductionRepository {

    IncomeDeduction save(IncomeDeduction incomeDeduction);

    IncomeDeduction getIncomeDeductionByUserIdAndScrapId(Long userId, Long scrapId);

}
