package com.jeksvp.bpd.service;

import com.jeksvp.bpd.web.dto.request.access.AccessRequest;

public interface AccessService {

    void sendAccessRequest(AccessRequest accessRequest);
}
