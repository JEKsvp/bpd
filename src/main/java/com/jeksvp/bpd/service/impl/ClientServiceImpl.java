package com.jeksvp.bpd.service.impl;

import com.jeksvp.bpd.domain.entity.access.client.ClientAccessList;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.repository.ClientAccessRepository;
import com.jeksvp.bpd.service.ClientService;
import com.jeksvp.bpd.web.dto.response.ClientAccessResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientAccessRepository clientAccessRepository;

    public ClientServiceImpl(ClientAccessRepository clientAccessRepository) {
        this.clientAccessRepository = clientAccessRepository;
    }

    @Override
    public List<ClientAccessResponse> getAccessedClientsOfUser(String username) {
        return clientAccessRepository.findById(username)
                .orElseThrow(() -> new ApiException(ApiErrorContainer.CLIENTS_NOT_FOUND))
                .getAccesses().stream()
                .map(ClientAccessResponse::create)
                .collect(Collectors.toList());
    }

    @Override
    public void createAccessClientsList(String username) {
        clientAccessRepository.save(ClientAccessList.create(username));
    }
}
