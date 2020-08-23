package com.jeksvp.bpd.service.impl;

import com.jeksvp.bpd.domain.entity.Role;
import com.jeksvp.bpd.domain.entity.User;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.kafka.dto.access.AccessRequestMessage;
import com.jeksvp.bpd.kafka.producer.AccessRequestProducer;
import com.jeksvp.bpd.repository.UserRepository;
import com.jeksvp.bpd.service.AccessService;
import com.jeksvp.bpd.utils.ClockSource;
import com.jeksvp.bpd.utils.SecurityUtils;
import com.jeksvp.bpd.utils.UuidSource;
import com.jeksvp.bpd.web.dto.request.access.AccessRequest;
import org.springframework.stereotype.Service;

@Service
public class AccessServiceImpl implements AccessService {

    private final UserRepository userRepository;
    private final AccessRequestProducer accessRequestProducer;
    private final ClockSource clockSource;
    private final UuidSource uuidSource;

    public AccessServiceImpl(UserRepository userRepository, AccessRequestProducer accessRequestProducer, ClockSource clockSource, UuidSource uuidSource) {
        this.userRepository = userRepository;
        this.accessRequestProducer = accessRequestProducer;
        this.clockSource = clockSource;
        this.uuidSource = uuidSource;
    }

    @Override
    public void sendAccessRequest(AccessRequest accessRequest) {
        User toUser = userRepository.findById(accessRequest.getUsername())
                .orElseThrow(() -> new ApiException(ApiErrorContainer.USER_NOT_FOUND));
        if (!SecurityUtils.hasRole(Role.CLIENT) || !toUser.hasRole(Role.THERAPIST)) {
            throw new ApiException(ApiErrorContainer.USER_NOT_FOUND);
        }
        AccessRequestMessage accessRequestMessage = AccessRequestMessage.create(accessRequest.getUsername(), clockSource, uuidSource);
        accessRequestProducer.sendAccessRequestMessage(accessRequestMessage);
    }
}
