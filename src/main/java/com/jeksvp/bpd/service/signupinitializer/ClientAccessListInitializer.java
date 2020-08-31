package com.jeksvp.bpd.service.signupinitializer;

import com.jeksvp.bpd.domain.entity.Role;
import com.jeksvp.bpd.domain.entity.User;
import com.jeksvp.bpd.service.AccessListService;
import com.jeksvp.bpd.service.SignUpInitializer;
import org.springframework.stereotype.Component;

@Component
public class ClientAccessListInitializer implements SignUpInitializer {

    private final AccessListService accessListService;

    public ClientAccessListInitializer(AccessListService accessListService) {
        this.accessListService = accessListService;
    }

    @Override
    public boolean shouldCreate(User user) {
        return true;
    }

    @Override
    public void createEntityFor(User user) {
        accessListService.createAccessClientsList(user.getUsername());
    }
}
