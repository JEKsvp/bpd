package com.jeksvp.bpd.service.impl;

import com.jeksvp.bpd.domain.entity.Role;
import com.jeksvp.bpd.domain.entity.User;
import com.jeksvp.bpd.domain.entity.access.therapist.TherapistAccess;
import com.jeksvp.bpd.domain.entity.access.therapist.TherapistAccessList;
import com.jeksvp.bpd.domain.entity.access.client.ClientAccess;
import com.jeksvp.bpd.domain.entity.access.client.ClientAccessList;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.kafka.dto.creator.TherapistAccessCreator;
import com.jeksvp.bpd.kafka.dto.access.AccessRequestMsg;
import com.jeksvp.bpd.kafka.dto.creator.ClientAccessCreator;
import com.jeksvp.bpd.kafka.dto.updater.TherapistAccessUpdater;
import com.jeksvp.bpd.kafka.dto.updater.ClientAccessUpdater;
import com.jeksvp.bpd.kafka.producer.AccessRequestProducer;
import com.jeksvp.bpd.repository.TherapistAccessRepository;
import com.jeksvp.bpd.repository.ClientAccessRepository;
import com.jeksvp.bpd.repository.UserRepository;
import com.jeksvp.bpd.service.AccessService;
import com.jeksvp.bpd.utils.SecurityUtils;
import com.jeksvp.bpd.web.dto.creator.PendingAccessRequestMsgCreator;
import com.jeksvp.bpd.web.dto.creator.PendingTherapistAccessCreator;
import com.jeksvp.bpd.web.dto.request.access.AccessRequest;
import org.springframework.stereotype.Service;

@Service
public class AccessServiceImpl implements AccessService {

    private final UserRepository userRepository;
    private final AccessRequestProducer accessRequestProducer;
    private final PendingAccessRequestMsgCreator pendingAccessRequestMsgCreator;
    private final PendingTherapistAccessCreator pendingTherapistAccessCreator;
    private final TherapistAccessRepository therapistAccessRepository;
    private final ClientAccessRepository clientAccessRepository;
    private final TherapistAccessUpdater therapistAccessUpdater;
    private final TherapistAccessCreator therapistAccessCreator;
    private final ClientAccessUpdater clientAccessUpdater;
    private final ClientAccessCreator clientAccessCreator;

    public AccessServiceImpl(UserRepository userRepository,
                             AccessRequestProducer accessRequestProducer,
                             PendingAccessRequestMsgCreator pendingAccessRequestMsgCreator,
                             PendingTherapistAccessCreator pendingTherapistAccessCreator,
                             TherapistAccessRepository therapistAccessRepository, ClientAccessRepository clientAccessRepository, TherapistAccessUpdater therapistAccessUpdater, TherapistAccessCreator therapistAccessCreator, ClientAccessUpdater clientAccessUpdater, ClientAccessCreator clientAccessCreator) {
        this.userRepository = userRepository;
        this.accessRequestProducer = accessRequestProducer;
        this.pendingAccessRequestMsgCreator = pendingAccessRequestMsgCreator;
        this.pendingTherapistAccessCreator = pendingTherapistAccessCreator;
        this.therapistAccessRepository = therapistAccessRepository;
        this.clientAccessRepository = clientAccessRepository;
        this.therapistAccessUpdater = therapistAccessUpdater;
        this.therapistAccessCreator = therapistAccessCreator;
        this.clientAccessUpdater = clientAccessUpdater;
        this.clientAccessCreator = clientAccessCreator;
    }

    @Override
//    todo do in transaction
    public void sendAccessRequest(AccessRequest accessRequest) {
        User toUser = userRepository.findById(accessRequest.getUsername())
                .orElseThrow(() -> new ApiException(ApiErrorContainer.USER_NOT_FOUND));
        if (!SecurityUtils.hasRole(Role.CLIENT) || !toUser.hasRole(Role.THERAPIST)) {
            throw new ApiException(ApiErrorContainer.USER_NOT_FOUND);
        }

        ClientAccessList clientAccessList = clientAccessRepository.findById(SecurityUtils.getCurrentUserName())
                .orElseThrow(() -> new ApiException(ApiErrorContainer.CLIENT_ACCESS_LIST_NOT_FOUND));

        AccessRequestMsg accessRequestMsg = pendingAccessRequestMsgCreator.create(accessRequest);
        accessRequestProducer.sendAccessRequestMessage(accessRequestMsg);

        ClientAccess clientAccess = pendingTherapistAccessCreator.create(accessRequest);
        clientAccessList.addAccess(clientAccess);
        clientAccessRepository.save(clientAccessList);
    }

    @Override
//    todo do in transaction
    public void updateAccessStatus(AccessRequestMsg accessRequestMsg) {
        updateClientAccessList(accessRequestMsg);
        updateTherapistAccessList(accessRequestMsg);
    }

    private void updateClientAccessList(AccessRequestMsg accessRequestMsg) {
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

    private void updateTherapistAccessList(AccessRequestMsg accessRequestMsg) {
        String therapistUsername = accessRequestMsg.getToTherapistUsername();
        TherapistAccessList therapistAccessList = therapistAccessRepository.findById(therapistUsername)
                .orElseThrow(() -> new ApiException(ApiErrorContainer.THERAPISTS_ACCESS_LIST_NOT_FOUND));
        String clientUsername = accessRequestMsg.getFromClientUsername();
        if (therapistAccessList.hasAccessStatusFor(clientUsername)) {
            TherapistAccess therapistAccess = therapistAccessList.findAccess(clientUsername).orElseThrow();
            therapistAccessUpdater.update(accessRequestMsg, therapistAccess);
        } else {
            TherapistAccess therapistAccess = therapistAccessCreator.create(accessRequestMsg);
            therapistAccessList.addAccess(therapistAccess);
        }
        therapistAccessList.removeDeclinedAccesses();
        therapistAccessRepository.save(therapistAccessList);
    }
}
