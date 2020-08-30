package com.jeksvp.bpd.service;

import com.jeksvp.bpd.web.dto.request.therapist.TherapistAccessFilter;
import com.jeksvp.bpd.web.dto.request.therapist.TherapistPageableFilter;
import com.jeksvp.bpd.web.dto.response.paging.PageableDto;
import com.jeksvp.bpd.web.dto.response.therapist.TherapistAccessResponse;
import com.jeksvp.bpd.web.dto.response.therapist.TherapistResponse;

import java.util.List;

public interface TherapistService {
    List<TherapistAccessResponse> getTherapistAccesses(String username, TherapistAccessFilter filter);

    void createAccessTherapistsList(String username);

    PageableDto<TherapistResponse> getTherapists(TherapistPageableFilter filter);
}
