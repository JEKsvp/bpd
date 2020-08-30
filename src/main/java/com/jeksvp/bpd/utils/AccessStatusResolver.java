package com.jeksvp.bpd.utils;

import com.jeksvp.bpd.domain.entity.access.AccessStatus;
import com.jeksvp.bpd.kafka.dto.access.AccessStatusMsg;
import com.jeksvp.bpd.web.dto.request.access.AccessStatusRequest;

public class AccessStatusResolver {

    public static AccessStatus resolve(AccessStatusMsg status) {
        switch (status) {
            case PENDING:
                return AccessStatus.PENDING;
            case ACCEPT:
                return AccessStatus.ACCEPT;
            case DECLINE:
                return AccessStatus.DECLINE;
            default:
                throw new IllegalArgumentException("Access status did not resolve");
        }
    }

    public static AccessStatus resolve(AccessStatusRequest status) {
        switch (status) {
            case PENDING:
                return AccessStatus.PENDING;
            case ACCEPT:
                return AccessStatus.ACCEPT;
            case DECLINE:
                return AccessStatus.DECLINE;
            default:
                throw new IllegalArgumentException("Access status did not resolve");
        }
    }

    public static AccessStatusMsg resolveMsg(AccessStatusRequest status) {
        switch (status) {
            case PENDING:
                return AccessStatusMsg.PENDING;
            case ACCEPT:
                return AccessStatusMsg.ACCEPT;
            case DECLINE:
                return AccessStatusMsg.DECLINE;
            default:
                throw new IllegalArgumentException("Access status did not resolve");
        }
    }
}
