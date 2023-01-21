package app.api.refund.api.scrap;

import app.api.refund.api.scrap.response.ScrapResponse;
import app.api.refund.business.scrapservice.application.scrap.ScrapHandler;
import app.api.refund.config.security.CustomUserDetails;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Tag(name = "Scrap API", description = "스크랩 요청관련 API")
@RestController
public class ScrapController {

    private final ScrapHandler scrapService;

    public ScrapController(ScrapHandler scrapService) {
        this.scrapService = scrapService;
    }

    @Tag(name = "Scrap API", description = "스크랩 요청관련 API")
    @ApiOperation(
            value = "스크랩데이터 조회 요청"
            , notes = "access token 유효해야 재발급 가능")
    @GetMapping("szs/scrap")
    public ResponseEntity<ScrapResponse> getScrapInfo(
            @ApiIgnore @RequestHeader(value = "authorization") String accessToken,
            @ApiIgnore @AuthenticationPrincipal CustomUserDetails userDetails) {
        boolean status = scrapService.getScrapData(userDetails.getUserId(), accessToken);

        return ResponseEntity.ok(new ScrapResponse(status));
    }

}
