package app.api.refund.api.scrap;

import app.api.refund.api.scrap.response.ScrapResponse;
import app.api.refund.business.scrapservice.application.scrap.ScrapHandler;
import app.api.refund.config.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScrapController {

    private final ScrapHandler scrapService;

    public ScrapController(ScrapHandler scrapService) {
        this.scrapService = scrapService;
    }

    @GetMapping("szs/scrap")
    public ResponseEntity<ScrapResponse> getScrapInfo(
            @RequestHeader(value = "authorization") String accessToken,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        boolean status = scrapService.getScrapData(userDetails.getUserId(), accessToken);

        return ResponseEntity.ok(new ScrapResponse(status));
    }

}
