package com.jeksvp.bpd.kafka.producer;

import com.jeksvp.bpd.kafka.dto.access.AccessRequestMsg;

public interface AccessRequestProducer {

    void sendAccessRequestMessage(AccessRequestMsg accessRequestMsg);
}
