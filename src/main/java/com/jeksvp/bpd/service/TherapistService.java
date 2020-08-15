package com.jeksvp.bpd.service;

import com.jeksvp.bpd.web.dto.response.TherapistResponse;

import java.util.List;

public interface TherapistService {
    List<TherapistResponse> getTherapistsOfUser(String username);

    void createAccessTherapistsList(String username);
}
