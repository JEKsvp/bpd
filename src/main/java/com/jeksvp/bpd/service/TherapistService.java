package com.jeksvp.bpd.service;

import com.jeksvp.bpd.web.dto.response.TherapistAccessResponse;

import java.util.List;

public interface TherapistService {
    List<TherapistAccessResponse> getAccessedTherapistsOfUser(String username);

    void createAccessTherapistsList(String username);
}
