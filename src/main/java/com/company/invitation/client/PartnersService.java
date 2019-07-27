package com.company.invitation.client;

import com.company.invitation.domain.Partners;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(value = "partners", url="${url.get.partners:}")
public interface PartnersService {
    @RequestMapping(method = GET)
    Partners getPartners();
}