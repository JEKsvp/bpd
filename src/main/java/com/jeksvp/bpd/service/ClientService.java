package com.jeksvp.bpd.service;

import com.jeksvp.bpd.web.dto.request.client.ClientAccessFilter;
import com.jeksvp.bpd.web.dto.response.client.ClientAccessResponse;

import java.util.List;

public interface ClientService {

    List<ClientAccessResponse> getAccessedClientsOfUser(String username);

    List<ClientAccessResponse> getAccessedClientsOfUser(String username, ClientAccessFilter filter);

    void createAccessClientsList(String username);
}
