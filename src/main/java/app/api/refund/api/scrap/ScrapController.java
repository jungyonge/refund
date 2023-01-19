package app.api.refund.api.scrap;

import app.api.refund.business.scrapservice.application.scrap.ScrapService;
import app.api.refund.config.security.CustomUserDetails;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScrapController {

    private final ScrapService scrapService;

    public ScrapController(ScrapService scrapService) {
        this.scrapService = scrapService;
    }

    @GetMapping("szs/scrap")
    public void getScrapInfo(@RequestHeader(value = "authorization") String accessToken ,@AuthenticationPrincipal CustomUserDetails userDetails){
        scrapService.getScrapData(userDetails.getUserId(), accessToken);
    }

}
