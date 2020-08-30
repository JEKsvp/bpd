package com.jeksvp.bpd.kafka.dto.updater;

import com.jeksvp.bpd.domain.entity.access.AccessStatus;
import com.jeksvp.bpd.domain.entity.access.therapist.TherapistAccess;
import com.jeksvp.bpd.kafka.dto.access.AccessRequestMsg;
import com.jeksvp.bpd.utils.AccessStatusResolver;
import com.jeksvp.bpd.support.Updater;
import com.jeksvp.bpd.utils.ClockSource;
import org.springframework.stereotype.Component;

@Component
public class TherapistAccessUpdater implements Updater<AccessRequestMsg, TherapistAccess> {

    private final ClockSource clockSource;

    public TherapistAccessUpdater(ClockSource clockSource) {
        this.clockSource = clockSource;
    }

    @Override
    public void update(AccessRequestMsg accessRequestMsg, TherapistAccess therapistAccess) {
        AccessStatus accessStatus = AccessStatusResolver.resolve(accessRequestMsg.getStatus());
        therapistAccess.update(accessStatus, clockSource);
    }
}
