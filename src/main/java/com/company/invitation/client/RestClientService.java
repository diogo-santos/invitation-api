package com.company.invitation.client;

import com.company.invitation.domain.Countries;
import com.company.invitation.domain.Partners;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.springframework.http.HttpMethod.POST;

@Service
public class RestClientService {
    @Autowired
    private final RestTemplate restTemplate;
    @Autowired
    private final Environment env;

    public RestClientService(RestTemplate restTemplate, Environment env) {
        this.restTemplate = restTemplate;
        this.env = env;
    }

    public Partners getPartners() {
        String url = env.getRequiredProperty("url.get.partners");
        return restTemplate.getForObject(url, Partners.class);
    }

    public ResponseEntity postCountries(Countries countries) {
        String url = env.getRequiredProperty("url.post.countries");
        return restTemplate.exchange(url, POST, new HttpEntity<>(countries), Map.class);
    }
}