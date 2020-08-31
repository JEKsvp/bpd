package com.jeksvp.bpd.service;

import com.jeksvp.bpd.web.dto.request.therapist.TherapistPageableFilter;
import com.jeksvp.bpd.web.dto.response.paging.PageableDto;
import com.jeksvp.bpd.web.dto.response.therapist.TherapistResponse;

public interface TherapistService {

    PageableDto<TherapistResponse> getTherapists(TherapistPageableFilter filter);

    TherapistResponse getTherapistByUsername(String username);
}
