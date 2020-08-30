package com.jeksvp.bpd.service.access;

import com.jeksvp.bpd.kafka.dto.access.AccessRequestMsg;

public interface AccessMessageProcessor {

    void process(AccessRequestMsg accessRequestMsg);
}
