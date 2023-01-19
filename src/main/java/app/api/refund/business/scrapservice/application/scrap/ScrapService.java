package app.api.refund.business.scrapservice.application.scrap;

import app.api.refund.business.scrapservice.domain.scrap.Scrap;
import app.api.refund.business.scrapservice.domain.scrap.SzsScrapService;
import app.api.refund.business.userservice.domain.UserDomainValidationMessage;
import app.api.refund.business.userservice.domain.user.UserRepository;
import app.api.refund.domain.DomainValidationException;
import org.springframework.stereotype.Service;

@Service
public class ScrapService {

    private final SzsScrapService szsScrapService;

    private final UserRepository userRepository;

    public ScrapService(SzsScrapService szsScrapService, UserRepository userRepository) {
        this.szsScrapService = szsScrapService;
        this.userRepository = userRepository;
    }

    public boolean getScrapData(String userId, String accessToken){

        userRepository.getUserByUserId(userId).map(user -> {
            szsScrapService.getScrapData(user.getName(), user.getRegNo(), accessToken);
            return true;
        }).orElseThrow(() -> new DomainValidationException(UserDomainValidationMessage.USER_NOT_FOUND));

        return false;
    }
}
