package com.company.invitation.client;

import com.company.invitation.domain.Partners;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class PartnersServiceFallback implements PartnersService {
    @Override
    public Partners getPartners() {
        return new Partners(Collections.emptyList());
    }
}