package app.api.refund.business.scrapservice.domain.scrap;

public interface PayRepository {

    Pay save(Pay pay);

    Pay getPayByUserId(Long userId);
}
