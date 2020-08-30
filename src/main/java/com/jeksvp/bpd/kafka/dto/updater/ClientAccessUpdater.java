package com.jeksvp.bpd.kafka.dto.updater;

import com.jeksvp.bpd.domain.entity.access.AccessStatus;
import com.jeksvp.bpd.domain.entity.access.client.ClientAccess;
import com.jeksvp.bpd.kafka.dto.access.AccessRequestMsg;
import com.jeksvp.bpd.utils.AccessStatusResolver;
import com.jeksvp.bpd.support.Updater;
import com.jeksvp.bpd.utils.ClockSource;
import org.springframework.stereotype.Component;

@Component
public class ClientAccessUpdater implements Updater<AccessRequestMsg, ClientAccess> {


    private final ClockSource clockSource;

    public ClientAccessUpdater(ClockSource clockSource) {
        this.clockSource = clockSource;
    }

    @Override
    public void update(AccessRequestMsg accessRequestMsg, ClientAccess clientAccess) {
        AccessStatus accessStatus = AccessStatusResolver.resolve(accessRequestMsg.getStatus());
        clientAccess.update(accessStatus, clockSource);
    }


}
