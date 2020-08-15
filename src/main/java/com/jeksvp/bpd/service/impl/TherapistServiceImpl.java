package com.jeksvp.bpd.service.impl;

import com.jeksvp.bpd.domain.entity.access.therapist.TherapistAccessList;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.repository.TherapistAccessRepository;
import com.jeksvp.bpd.service.TherapistService;
import com.jeksvp.bpd.web.dto.response.TherapistAccessResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TherapistServiceImpl implements TherapistService {

    private final TherapistAccessRepository therapistAccessRepository;

    public TherapistServiceImpl(TherapistAccessRepository therapistAccessRepository) {
        this.therapistAccessRepository = therapistAccessRepository;
    }

    @Override
    public List<TherapistAccessResponse> getAccessedTherapistsOfUser(String username) {
        return therapistAccessRepository.findById(username)
                .orElseThrow(() -> new ApiException(ApiErrorContainer.THERAPISTS_NOT_FOUND))
                .getAccesses().stream()
                .map(TherapistAccessResponse::create)
                .collect(Collectors.toList());
    }

    @Override
    public void createAccessTherapistsList(String username) {
        therapistAccessRepository.save(TherapistAccessList.create(username));
    }
}
