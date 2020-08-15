package com.jeksvp.bpd.web.controller;

import com.jeksvp.bpd.service.ClientService;
import com.jeksvp.bpd.utils.SecurityUtils;
import com.jeksvp.bpd.web.dto.response.ClientAccessResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/api/v1/users/current/clients")
    public List<ClientAccessResponse> getAccessedClientsList() {
        String currentUserName = SecurityUtils.getCurrentUserName();
        return clientService.getAccessedClientsOfUser(currentUserName);
    }
}
