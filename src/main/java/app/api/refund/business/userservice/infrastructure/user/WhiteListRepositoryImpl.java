package app.api.refund.business.userservice.infrastructure.user;

import app.api.refund.business.userservice.domain.user.WhiteList;
import app.api.refund.business.userservice.domain.user.WhiteListRepository;
import org.springframework.stereotype.Repository;

@Repository
public class WhiteListRepositoryImpl implements WhiteListRepository {

    private final WhiteListJpaRepository whiteListJpaRepository;

    public WhiteListRepositoryImpl(WhiteListJpaRepository whiteListJpaRepository) {
        this.whiteListJpaRepository = whiteListJpaRepository;
    }

    @Override
    public WhiteList getWhiteListByName(String name) {
        return whiteListJpaRepository.findTopByName(name);
    }
}
