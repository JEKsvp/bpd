package com.jeksvp.bpd.kafka.util;

import com.jeksvp.bpd.domain.entity.access.AccessStatus;
import com.jeksvp.bpd.kafka.dto.access.AccessStatusMsg;

public class AccessStatusResolver {

    public static AccessStatus resolve(AccessStatusMsg status) {
        switch (status) {
            case PENDING:
                return AccessStatus.PENDING;
            case ACCEPT:
                return AccessStatus.ACCEPT;
            default:
                return AccessStatus.DECLINE;
        }
    }
}
