package com.jeksvp.bpd.service.access.impl;

import com.jeksvp.bpd.domain.entity.Role;
import com.jeksvp.bpd.domain.entity.User;
import com.jeksvp.bpd.domain.entity.access.client.ClientAccess;
import com.jeksvp.bpd.domain.entity.access.client.ClientAccessList;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.kafka.dto.access.AccessRequestMsg;
import com.jeksvp.bpd.kafka.producer.AccessRequestProducer;
import com.jeksvp.bpd.repository.ClientAccessRepository;
import com.jeksvp.bpd.repository.UserRepository;
import com.jeksvp.bpd.service.access.AccessMessageProcessor;
import com.jeksvp.bpd.service.access.AccessService;
import com.jeksvp.bpd.utils.SecurityUtils;
import com.jeksvp.bpd.web.dto.creator.PendingAccessRequestMsgCreator;
import com.jeksvp.bpd.web.dto.creator.PendingTherapistAccessCreator;
import com.jeksvp.bpd.web.dto.request.access.AccessRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessServiceImpl implements AccessService {

    private final UserRepository userRepository;
    private final AccessRequestProducer accessRequestProducer;
    private final PendingAccessRequestMsgCreator pendingAccessRequestMsgCreator;
    private final PendingTherapistAccessCreator pendingTherapistAccessCreator;
    private final ClientAccessRepository clientAccessRepository;
    private final List<AccessMessageProcessor> accessMessageProcessors;

    public AccessServiceImpl(UserRepository userRepository,
                             AccessRequestProducer accessRequestProducer,
                             PendingAccessRequestMsgCreator pendingAccessRequestMsgCreator,
                             PendingTherapistAccessCreator pendingTherapistAccessCreator,
                             ClientAccessRepository clientAccessRepository,
                             List<AccessMessageProcessor> accessMessageProcessors) {
        this.userRepository = userRepository;
        this.accessRequestProducer = accessRequestProducer;
        this.pendingAccessRequestMsgCreator = pendingAccessRequestMsgCreator;
        this.pendingTherapistAccessCreator = pendingTherapistAccessCreator;
        this.clientAccessRepository = clientAccessRepository;
        this.accessMessageProcessors = accessMessageProcessors;
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
        accessMessageProcessors.forEach(processor -> processor.process(accessRequestMsg));
    }
}
