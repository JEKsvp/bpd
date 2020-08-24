package com.jeksvp.bpd.service.impl;

import com.jeksvp.bpd.domain.entity.Role;
import com.jeksvp.bpd.domain.entity.User;
import com.jeksvp.bpd.domain.entity.access.therapist.TherapistAccess;
import com.jeksvp.bpd.domain.entity.access.therapist.TherapistAccessList;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.kafka.dto.access.AccessRequestMsg;
import com.jeksvp.bpd.web.dto.creator.PendingAccessRequestMsgCreator;
import com.jeksvp.bpd.kafka.producer.AccessRequestProducer;
import com.jeksvp.bpd.repository.TherapistAccessRepository;
import com.jeksvp.bpd.repository.UserRepository;
import com.jeksvp.bpd.service.AccessService;
import com.jeksvp.bpd.utils.SecurityUtils;
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

    public AccessServiceImpl(UserRepository userRepository,
                             AccessRequestProducer accessRequestProducer,
                             PendingAccessRequestMsgCreator pendingAccessRequestMsgCreator,
                             PendingTherapistAccessCreator pendingTherapistAccessCreator, TherapistAccessRepository therapistAccessRepository) {
        this.userRepository = userRepository;
        this.accessRequestProducer = accessRequestProducer;
        this.pendingAccessRequestMsgCreator = pendingAccessRequestMsgCreator;
        this.pendingTherapistAccessCreator = pendingTherapistAccessCreator;
        this.therapistAccessRepository = therapistAccessRepository;
    }

    @Override
//    todo do in transaction
    public void sendAccessRequest(AccessRequest accessRequest) {
        User toUser = userRepository.findById(accessRequest.getUsername())
                .orElseThrow(() -> new ApiException(ApiErrorContainer.USER_NOT_FOUND));
        if (!SecurityUtils.hasRole(Role.CLIENT) || !toUser.hasRole(Role.THERAPIST)) {
            throw new ApiException(ApiErrorContainer.USER_NOT_FOUND);
        }

        TherapistAccessList therapistAccessList = therapistAccessRepository.findById(SecurityUtils.getCurrentUserName())
                .orElseThrow(() -> new ApiException(ApiErrorContainer.THERAPISTS_ACCESS_LIST_NOT_FOUND));

        AccessRequestMsg accessRequestMsg = pendingAccessRequestMsgCreator.create(accessRequest);
        accessRequestProducer.sendAccessRequestMessage(accessRequestMsg);

        TherapistAccess therapistAccess = pendingTherapistAccessCreator.create(accessRequest);
        therapistAccessList.addAccess(therapistAccess);
        therapistAccessRepository.save(therapistAccessList);
    }
}
