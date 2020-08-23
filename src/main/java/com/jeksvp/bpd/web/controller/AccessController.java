package com.jeksvp.bpd.web.controller;

import com.jeksvp.bpd.service.AccessService;
import com.jeksvp.bpd.web.dto.request.access.AccessRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/access-request")
public class AccessController {

    private final AccessService accessService;

    public AccessController(AccessService accessService) {
        this.accessService = accessService;
    }

    @PostMapping
    public void requestAccess(@RequestBody @Valid AccessRequest accessRequest) {
        accessService.sendAccessRequest(accessRequest);
    }
}
