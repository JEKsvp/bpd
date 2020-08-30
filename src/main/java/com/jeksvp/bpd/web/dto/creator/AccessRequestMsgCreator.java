package com.jeksvp.bpd.web.dto.creator;

import com.jeksvp.bpd.kafka.dto.access.AccessRequestMsg;
import com.jeksvp.bpd.kafka.dto.access.AccessStatusMsg;
import com.jeksvp.bpd.support.Creator;
import com.jeksvp.bpd.utils.AccessStatusResolver;
import com.jeksvp.bpd.utils.ClockSource;
import com.jeksvp.bpd.utils.UuidSource;
import com.jeksvp.bpd.web.dto.request.access.AccessRequest;
import org.springframework.stereotype.Component;

@Component
public class AccessRequestMsgCreator implements Creator<AccessRequest, AccessRequestMsg> {

    private final ClockSource clockSource;
    private final UuidSource uuidSource;

    public AccessRequestMsgCreator(ClockSource clockSource, UuidSource uuidSource) {
        this.clockSource = clockSource;
        this.uuidSource = uuidSource;
    }

    @Override
    public AccessRequestMsg create(AccessRequest accessRequest) {
        return AccessRequestMsg.create(
                accessRequest.getUsername(),
                AccessStatusResolver.resolveMsg(accessRequest.getStatus()),
                clockSource,
                uuidSource
        );
    }
}
