package com.jeksvp.bpd.service.signupinitializer;

import com.jeksvp.bpd.domain.entity.Role;
import com.jeksvp.bpd.domain.entity.User;
import com.jeksvp.bpd.service.ClientService;
import com.jeksvp.bpd.service.SignUpInitializer;
import org.springframework.stereotype.Component;

@Component
public class ClientAccessListInitializer implements SignUpInitializer {

    private final ClientService clientService;

    public ClientAccessListInitializer(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public boolean shouldCreate(User user) {
        return user.getRoles().contains(Role.CLIENT);
    }

    @Override
    public void createEntityFor(User user) {
        clientService.createAccessClientsList(user.getUsername());
    }
}
