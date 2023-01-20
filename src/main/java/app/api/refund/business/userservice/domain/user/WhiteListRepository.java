package app.api.refund.business.userservice.domain.user;

public interface WhiteListRepository {
    WhiteList getWhiteListByName(String name);
}
