package com.jeksvp.bpd.service.impl;

import com.jeksvp.bpd.domain.entity.access.client.ClientAccessList;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.repository.ClientAccessRepository;
import com.jeksvp.bpd.service.ClientService;
import com.jeksvp.bpd.web.dto.request.client.ClientAccessFilter;
import com.jeksvp.bpd.web.dto.response.client.ClientAccessResponse;
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
    public List<ClientAccessResponse> getAccessedTherapistsOfUser(String username) {
        return getAccessedTherapistsOfUser(username, ClientAccessFilter.builder().build());
    }

    @Override
    public List<ClientAccessResponse> getAccessedTherapistsOfUser(String username, ClientAccessFilter filter) {
        return clientAccessRepository.findById(username)
                .orElseThrow(() -> new ApiException(ApiErrorContainer.CLIENT_ACCESS_LIST_NOT_FOUND))
                .getAccesses().stream()
                .filter(filter::passed)
                .map(ClientAccessResponse::create)
                .collect(Collectors.toList());
    }

    @Override
    public void createAccessClientsList(String username) {
        clientAccessRepository.save(ClientAccessList.create(username));
    }
}
