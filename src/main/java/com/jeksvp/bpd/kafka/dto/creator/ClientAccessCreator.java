package com.jeksvp.bpd.kafka.dto.creator;

import com.jeksvp.bpd.domain.entity.access.client.ClientAccess;
import com.jeksvp.bpd.kafka.dto.access.AccessRequestMsg;
import com.jeksvp.bpd.utils.AccessStatusResolver;
import com.jeksvp.bpd.support.Creator;
import com.jeksvp.bpd.utils.ClockSource;
import org.springframework.stereotype.Component;

@Component
public class ClientAccessCreator implements Creator<AccessRequestMsg, ClientAccess> {

    private final ClockSource clockSource;

    public ClientAccessCreator(ClockSource clockSource) {
        this.clockSource = clockSource;
    }

    @Override
    public ClientAccess create(AccessRequestMsg accessStatusMsg) {
        return ClientAccess.create(
                accessStatusMsg.getToTherapistUsername(),
                AccessStatusResolver.resolve(accessStatusMsg.getStatus()),
                clockSource
        );
    }
}
