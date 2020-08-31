package com.jeksvp.bpd.service.access;

import com.jeksvp.bpd.kafka.dto.access.AccessRequestMsg;
import com.jeksvp.bpd.web.dto.request.access.AccessRequest;

public interface AccessService {

    void sendAccessRequest(AccessRequest accessRequest);

    void updateAccessStatus(AccessRequestMsg accessRequestMsg);
}
