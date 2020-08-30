package com.jeksvp.bpd.service.access.impl;

import com.jeksvp.bpd.domain.entity.access.therapist.TherapistAccess;
import com.jeksvp.bpd.domain.entity.access.therapist.TherapistAccessList;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.kafka.dto.access.AccessRequestMsg;
import com.jeksvp.bpd.kafka.dto.creator.TherapistAccessCreator;
import com.jeksvp.bpd.kafka.dto.updater.TherapistAccessUpdater;
import com.jeksvp.bpd.repository.TherapistAccessRepository;
import com.jeksvp.bpd.service.access.AccessMessageProcessor;
import org.springframework.stereotype.Component;

@Component
public class TherapistAccessMessageProcessor implements AccessMessageProcessor {

    private final TherapistAccessRepository therapistAccessRepository;
    private final TherapistAccessCreator therapistAccessCreator;
    private final TherapistAccessUpdater therapistAccessUpdater;

    public TherapistAccessMessageProcessor(TherapistAccessRepository therapistAccessRepository,
                                           TherapistAccessCreator therapistAccessCreator,
                                           TherapistAccessUpdater therapistAccessUpdater) {
        this.therapistAccessRepository = therapistAccessRepository;
        this.therapistAccessCreator = therapistAccessCreator;
        this.therapistAccessUpdater = therapistAccessUpdater;
    }

    @Override
    public void process(AccessRequestMsg accessRequestMsg) {
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
