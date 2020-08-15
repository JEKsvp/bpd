package com.jeksvp.bpd.web.controller;

import com.jeksvp.bpd.service.TherapistService;
import com.jeksvp.bpd.utils.SecurityUtils;
import com.jeksvp.bpd.web.dto.response.TherapistAccessResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TherapistController {

    private final TherapistService therapistService;

    public TherapistController(TherapistService therapistService) {
        this.therapistService = therapistService;
    }

    @GetMapping("/api/v1/users/current/therapists")
    public List<TherapistAccessResponse> getAccessedTerapistsList() {
        String currentUserName = SecurityUtils.getCurrentUserName();
        return therapistService.getAccessedTherapistsOfUser(currentUserName);
    }

}
