package com.jeksvp.bpd.service.access.impl;

import com.jeksvp.bpd.domain.entity.access.client.ClientAccess;
import com.jeksvp.bpd.domain.entity.access.client.ClientAccessList;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.kafka.dto.access.AccessRequestMsg;
import com.jeksvp.bpd.kafka.dto.creator.ClientAccessCreator;
import com.jeksvp.bpd.kafka.dto.updater.ClientAccessUpdater;
import com.jeksvp.bpd.repository.ClientAccessRepository;
import com.jeksvp.bpd.service.access.AccessMessageProcessor;
import org.springframework.stereotype.Component;

@Component
public class ClientAccessMessageProcessor implements AccessMessageProcessor {

    private final ClientAccessRepository clientAccessRepository;
    private final ClientAccessCreator clientAccessCreator;
    private final ClientAccessUpdater clientAccessUpdater;

    public ClientAccessMessageProcessor(ClientAccessRepository clientAccessRepository,
                                        ClientAccessCreator clientAccessCreator,
                                        ClientAccessUpdater clientAccessUpdater) {
        this.clientAccessRepository = clientAccessRepository;
        this.clientAccessCreator = clientAccessCreator;
        this.clientAccessUpdater = clientAccessUpdater;
    }

    @Override
    public void process(AccessRequestMsg accessRequestMsg) {
        String clientUsername = accessRequestMsg.getFromClientUsername();
        ClientAccessList clientAccessList = clientAccessRepository.findById(clientUsername)
                .orElseThrow(() -> new ApiException(ApiErrorContainer.CLIENT_ACCESS_LIST_NOT_FOUND));
        String therapistUsername = accessRequestMsg.getToTherapistUsername();

        if (clientAccessList.hasAccessStatusFor(therapistUsername)) {
            ClientAccess clientAccess = clientAccessList.findAccess(therapistUsername).orElseThrow();
            clientAccessUpdater.update(accessRequestMsg, clientAccess);
        } else {
            ClientAccess clientAccess = clientAccessCreator.create(accessRequestMsg);
            clientAccessList.addAccess(clientAccess);
        }
        clientAccessList.removeDeclinedAccesses();
        clientAccessRepository.save(clientAccessList);
    }
}
