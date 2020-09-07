package com.jeksvp.bpd.service.access.impl;

import com.jeksvp.bpd.domain.entity.access.Access;
import com.jeksvp.bpd.domain.entity.access.AccessList;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.kafka.dto.access.AccessRequestMsg;
import com.jeksvp.bpd.repository.AccessListRepository;
import com.jeksvp.bpd.service.access.AccessMessageProcessor;
import com.jeksvp.bpd.utils.AccessStatusResolver;
import com.jeksvp.bpd.utils.ClockSource;
import org.springframework.stereotype.Component;

@Component
public class AccessMessageProcessorImpl implements AccessMessageProcessor {

    private final AccessListRepository accessListRepository;
    private final ClockSource clockSource;

    public AccessMessageProcessorImpl(AccessListRepository accessListRepository, ClockSource clockSource) {
        this.accessListRepository = accessListRepository;
        this.clockSource = clockSource;
    }

    @Override
    public void process(AccessRequestMsg accessRequestMsg, String fromUsername, String toUsername) {
        AccessList accessList = accessListRepository.findById(fromUsername)
                .orElseThrow(() -> new ApiException(ApiErrorContainer.CLIENT_ACCESS_LIST_NOT_FOUND));

        if (accessList.hasAccessStatusFor(toUsername)) {
            Access access = accessList.findAccess(toUsername).orElseThrow();
            access.update(AccessStatusResolver.resolve(accessRequestMsg.getStatus()), clockSource);
        } else {
            Access access = Access.create(toUsername, AccessStatusResolver.resolve(accessRequestMsg.getStatus()), clockSource);
            accessList.addAccess(access);
        }
        accessList.removeDeclinedAccesses();
        accessListRepository.save(accessList);
    }
}
