package app.api.refund.business.userservice.infrastructure.user;

import app.api.refund.business.userservice.domain.user.WhiteList;
import org.springframework.data.repository.CrudRepository;

public interface WhiteListJpaRepository extends CrudRepository<WhiteList, Long> {

    WhiteList findTopByName(String name);
}
