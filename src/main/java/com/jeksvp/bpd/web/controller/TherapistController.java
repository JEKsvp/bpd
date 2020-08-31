package com.jeksvp.bpd.web.controller;

import com.jeksvp.bpd.service.TherapistService;
import com.jeksvp.bpd.utils.SecurityUtils;
import com.jeksvp.bpd.web.dto.request.therapist.TherapistAccessFilter;
import com.jeksvp.bpd.web.dto.request.therapist.TherapistPageableFilter;
import com.jeksvp.bpd.web.dto.response.paging.PageableDto;
import com.jeksvp.bpd.web.dto.response.therapist.TherapistAccessResponse;
import com.jeksvp.bpd.web.dto.response.therapist.TherapistResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TherapistController {

    private final TherapistService therapistService;

    public TherapistController(TherapistService therapistService) {
        this.therapistService = therapistService;
    }

    @GetMapping("/users/current/therapist-accesses")
    public List<TherapistAccessResponse> getAccessedTherapistsList(TherapistAccessFilter filter) {
        String currentUserName = SecurityUtils.getCurrentUserName();
        return therapistService.getTherapistAccesses(currentUserName, filter);
    }

    @GetMapping("/therapists")
    public PageableDto<TherapistResponse> getTherapists(TherapistPageableFilter filter) {
        return therapistService.getTherapists(filter);
    }
}
