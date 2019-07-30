package com.company.invitation.client;

import com.company.invitation.domain.Countries;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CountriesServiceFallback implements CountriesService {
    @Override
    public ResponseEntity<Map> postCountries(Countries countries) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(new HashMap<String, String>() {{
                    put("message", "Error submitting invitation data");
                }});
    }
}