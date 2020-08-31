package com.jeksvp.bpd.service.access.impl;

import com.jeksvp.bpd.domain.entity.access.therapist.TherapistAccess;
import com.jeksvp.bpd.domain.entity.access.therapist.TherapistAccessList;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.kafka.dto.access.AccessRequestMsg;
import com.jeksvp.bpd.repository.TherapistAccessRepository;
import com.jeksvp.bpd.service.access.AccessMessageProcessor;
import com.jeksvp.bpd.utils.AccessStatusResolver;
import com.jeksvp.bpd.utils.ClockSource;
import org.springframework.stereotype.Component;

@Component
public class TherapistAccessMessageProcessor implements AccessMessageProcessor {

    private final TherapistAccessRepository therapistAccessRepository;
    private final ClockSource clockSource;

    public TherapistAccessMessageProcessor(TherapistAccessRepository therapistAccessRepository, ClockSource clockSource) {
        this.therapistAccessRepository = therapistAccessRepository;
        this.clockSource = clockSource;
    }

    @Override
    public void process(AccessRequestMsg accessRequestMsg, String therapistUsername, String clientUsername) {
        TherapistAccessList therapistAccessList = therapistAccessRepository.findById(therapistUsername)
                .orElseThrow(() -> new ApiException(ApiErrorContainer.THERAPISTS_ACCESS_LIST_NOT_FOUND));
        if (therapistAccessList.hasAccessStatusFor(clientUsername)) {
            TherapistAccess therapistAccess = therapistAccessList.findAccess(clientUsername).orElseThrow();
            therapistAccess.update(AccessStatusResolver.resolve(accessRequestMsg.getStatus()), clockSource);
        } else {
            TherapistAccess therapistAccess = TherapistAccess.create(clientUsername, AccessStatusResolver.resolve(accessRequestMsg.getStatus()), clockSource);
            therapistAccessList.addAccess(therapistAccess);
        }
        therapistAccessList.removeDeclinedAccesses();
        therapistAccessRepository.save(therapistAccessList);
    }
}
