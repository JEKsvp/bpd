package com.jeksvp.bpd.service.access.impl;

import com.jeksvp.bpd.domain.entity.access.client.ClientAccess;
import com.jeksvp.bpd.domain.entity.access.client.ClientAccessList;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.kafka.dto.access.AccessRequestMsg;
import com.jeksvp.bpd.repository.ClientAccessRepository;
import com.jeksvp.bpd.service.access.AccessMessageProcessor;
import com.jeksvp.bpd.utils.AccessStatusResolver;
import com.jeksvp.bpd.utils.ClockSource;
import org.springframework.stereotype.Component;

@Component
public class ClientAccessMessageProcessor implements AccessMessageProcessor {

    private final ClientAccessRepository clientAccessRepository;
    private final ClockSource clockSource;

    public ClientAccessMessageProcessor(ClientAccessRepository clientAccessRepository, ClockSource clockSource) {
        this.clientAccessRepository = clientAccessRepository;
        this.clockSource = clockSource;
    }

    @Override
    public void process(AccessRequestMsg accessRequestMsg, String clientUsername, String therapistUsername) {
        ClientAccessList clientAccessList = clientAccessRepository.findById(clientUsername)
                .orElseThrow(() -> new ApiException(ApiErrorContainer.CLIENT_ACCESS_LIST_NOT_FOUND));

        if (clientAccessList.hasAccessStatusFor(therapistUsername)) {
            ClientAccess clientAccess = clientAccessList.findAccess(therapistUsername).orElseThrow();
            clientAccess.update(AccessStatusResolver.resolve(accessRequestMsg.getStatus()), clockSource);
        } else {
            ClientAccess clientAccess = ClientAccess.create(therapistUsername, AccessStatusResolver.resolve(accessRequestMsg.getStatus()), clockSource);
            clientAccessList.addAccess(clientAccess);
        }
        clientAccessList.removeDeclinedAccesses();
        clientAccessRepository.save(clientAccessList);
    }
}
