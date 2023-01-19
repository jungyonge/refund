package app.api.refund.business.scrapservice.domain.scrap;

public interface ScrapRepository {

    Scrap save(Scrap scrap);

    Scrap getScrapByUserId(Long userId);
}
