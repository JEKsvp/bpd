package com.jeksvp.bpd.web.dto.creator;

import com.jeksvp.bpd.domain.entity.access.AccessStatus;
import com.jeksvp.bpd.domain.entity.access.client.ClientAccess;
import com.jeksvp.bpd.support.Creator;
import com.jeksvp.bpd.utils.ClockSource;
import com.jeksvp.bpd.web.dto.request.access.AccessRequest;
import org.springframework.stereotype.Component;

@Component
public class PendingTherapistAccessCreator implements Creator<AccessRequest, ClientAccess> {

    private final ClockSource clockSource;

    public PendingTherapistAccessCreator(ClockSource clockSource) {
        this.clockSource = clockSource;
    }

    @Override
    public ClientAccess create(AccessRequest accessRequest) {
        return ClientAccess.create(
                accessRequest.getUsername(),
                AccessStatus.PENDING,
                clockSource);
    }
}
