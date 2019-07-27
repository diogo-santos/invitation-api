package com.company.invitation.client;

import com.company.invitation.domain.Countries;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@FeignClient(value = "countries", url="${url.post.countries:}")
public interface CountriesService {
    @RequestMapping(method = POST)
    ResponseEntity<Map> postCountries(@RequestBody Countries countries);
}
