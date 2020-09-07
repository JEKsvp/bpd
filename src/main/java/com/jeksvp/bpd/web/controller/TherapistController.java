package com.jeksvp.bpd.web.controller;

import com.jeksvp.bpd.service.TherapistService;
import com.jeksvp.bpd.web.dto.request.therapist.TherapistPageableFilter;
import com.jeksvp.bpd.web.dto.response.paging.PageableDto;
import com.jeksvp.bpd.web.dto.response.therapist.TherapistResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TherapistController {

    private final TherapistService therapistService;

    public TherapistController(TherapistService therapistService) {
        this.therapistService = therapistService;
    }

    @GetMapping("/therapists")
    public PageableDto<TherapistResponse> getTherapists(TherapistPageableFilter filter) {
        return therapistService.getTherapists(filter);
    }

    @GetMapping("/therapists/{username}")
    public TherapistResponse getTherapistByUsername(@PathVariable String username) {
        return therapistService.getTherapistByUsername(username);
    }
}
