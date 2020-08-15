package com.jeksvp.bpd.service;

import com.jeksvp.bpd.web.dto.response.ClientAccessResponse;

import java.util.List;

public interface ClientService {

    List<ClientAccessResponse> getAccessedClientsOfUser(String username);

    void createAccessClientsList(String username);
}
