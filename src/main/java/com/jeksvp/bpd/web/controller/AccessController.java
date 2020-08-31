package com.jeksvp.bpd.web.controller;

import com.jeksvp.bpd.service.AccessListService;
import com.jeksvp.bpd.service.access.AccessService;
import com.jeksvp.bpd.utils.SecurityUtils;
import com.jeksvp.bpd.web.dto.request.access.AccessFilter;
import com.jeksvp.bpd.web.dto.request.access.AccessRequest;
import com.jeksvp.bpd.web.dto.response.access.AccessResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AccessController {

    private final AccessService accessService;
    private final AccessListService accessListService;

    public AccessController(AccessService accessService,
                            AccessListService accessListService) {
        this.accessService = accessService;
        this.accessListService = accessListService;
    }

    @PostMapping("/access-request")
    public void requestAccess(@RequestBody @Valid AccessRequest accessRequest) {
        accessService.sendAccessRequest(accessRequest);
    }

    @GetMapping("/users/current/accesses")
    public List<AccessResponse> getAccessedClientsListWithParams(AccessFilter filter) {
        String currentUserName = SecurityUtils.getCurrentUserName();
        return accessListService.getAccessesOfUser(currentUserName, filter);
    }


}
