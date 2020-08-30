package com.jeksvp.bpd.kafka.dto.creator;

import com.jeksvp.bpd.domain.entity.access.therapist.TherapistAccess;
import com.jeksvp.bpd.kafka.dto.access.AccessRequestMsg;
import com.jeksvp.bpd.utils.AccessStatusResolver;
import com.jeksvp.bpd.support.Creator;
import com.jeksvp.bpd.utils.ClockSource;
import org.springframework.stereotype.Component;

@Component
public class TherapistAccessCreator implements Creator<AccessRequestMsg, TherapistAccess> {

    private final ClockSource clockSource;

    public TherapistAccessCreator(ClockSource clockSource) {
        this.clockSource = clockSource;
    }

    @Override
    public TherapistAccess create(AccessRequestMsg accessStatusMsg) {
        return TherapistAccess.create(
                accessStatusMsg.getFromClientUsername(),
                AccessStatusResolver.resolve(accessStatusMsg.getStatus()),
                clockSource
        );
    }
}
