package com.jeksvp.bpd.web.controller;

import com.jeksvp.bpd.web.dto.request.access.AccessRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/access-request")
public class AccessController {

    @PostMapping
    public void requestAccess(@RequestBody AccessRequest accessRequest) {

    }
}
