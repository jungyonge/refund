package app.api.refund.business.scrapservice.domain.scrap;

public interface SzsScrapService {

    public SzsScrapData getScrapData(String name, String regNo, String accessToken);
}
