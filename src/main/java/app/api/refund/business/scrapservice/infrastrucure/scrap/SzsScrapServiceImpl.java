package app.api.refund.business.scrapservice.infrastrucure.scrap;

import app.api.refund.business.scrapservice.domain.scrap.SzsScrapData;
import app.api.refund.business.scrapservice.domain.scrap.SzsScrapService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SzsScrapServiceImpl implements SzsScrapService {

    private final RestTemplate restTemplate;
    private final String API_URL;

    public SzsScrapServiceImpl(RestTemplate restTemplate, @Value("${szs.scrap-api-url}") String api_url) {
        this.restTemplate = restTemplate;
        API_URL = api_url;
    }
    @Override
    public SzsScrapData getScrapData(String name, String regNo, String accessToken) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("regNo", regNo);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", accessToken);

        HttpEntity<Map> request = new HttpEntity<>(map, httpHeaders);
        SzsScrapData szsScrapData = restTemplate.postForObject(API_URL, request, SzsScrapData.class);

        return null;
    }
}
