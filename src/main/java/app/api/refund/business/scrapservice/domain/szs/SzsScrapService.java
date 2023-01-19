package app.api.refund.business.scrapservice.domain.szs;

public interface SzsScrapService {

    SzsScrapData getScrapData(String name, String regNo, String accessToken);
}
