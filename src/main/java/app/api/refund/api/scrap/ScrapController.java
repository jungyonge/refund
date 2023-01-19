package app.api.refund.api.scrap;

import app.api.refund.business.scrapservice.application.scrap.ScrapService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScrapController {

    private final ScrapService scrapServicåe;

    public ScrapController(ScrapService scrapServicåe) {
        this.scrapServicåe = scrapServicåe;
    }

    @PostMapping("szs/scrap")
    public void getScrapInfo(){
        scrapServicåe.getScrapInfo();
    }
}
