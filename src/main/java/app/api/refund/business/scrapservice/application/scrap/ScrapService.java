package app.api.refund.business.scrapservice.application.scrap;

import app.api.refund.business.scrapservice.domain.scrap.Scrap;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ScrapService {

    private final RestTemplate restTemplate;

    private final String API_URL;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ScrapService(RestTemplate restTemplate, @Value("szs.scrap-api-url") String api_url) {
        this.restTemplate = restTemplate;
        API_URL = api_url;
    }

    public Scrap getScrapInfo(){


        return null;
    }
}
