package com.jeksvp.bpd.service;

import com.jeksvp.bpd.web.dto.request.therapist.TherapistPageableFilter;
import com.jeksvp.bpd.web.dto.response.paging.PageableDto;
import com.jeksvp.bpd.web.dto.response.therapist.TherapistAccessResponse;
import com.jeksvp.bpd.web.dto.response.therapist.TherapistResponse;

import java.util.List;

public interface TherapistService {
    List<TherapistAccessResponse> getAccessedTherapistsOfUser(String username);

    void createAccessTherapistsList(String username);

    PageableDto<TherapistResponse> getTherapists(TherapistPageableFilter filter);
}
