package com.jeksvp.bpd.service.access;

import com.jeksvp.bpd.web.dto.request.access.AccessRequest;

public interface AccessRequestValidator {

    void validate(AccessRequest accessRequest);
}
