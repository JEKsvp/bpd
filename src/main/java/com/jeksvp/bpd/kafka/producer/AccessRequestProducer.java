package com.jeksvp.bpd.kafka.producer;

import com.jeksvp.bpd.kafka.dto.access.AccessRequestMessage;

public interface AccessRequestProducer {

    void sendAccessRequestMessage(AccessRequestMessage accessRequestMessage);
}
