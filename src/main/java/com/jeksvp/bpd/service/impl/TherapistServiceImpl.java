package com.jeksvp.bpd.service.impl;

import com.jeksvp.bpd.domain.entity.Role;
import com.jeksvp.bpd.domain.entity.User;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.repository.UserRepository;
import com.jeksvp.bpd.service.TherapistService;
import com.jeksvp.bpd.web.dto.request.therapist.TherapistPageableFilter;
import com.jeksvp.bpd.web.dto.response.paging.PageableDto;
import com.jeksvp.bpd.web.dto.response.therapist.TherapistResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TherapistServiceImpl implements TherapistService {

    private final UserRepository userRepository;

    public TherapistServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public PageableDto<TherapistResponse> getTherapists(TherapistPageableFilter filter) {
        PageRequest pageRequest = PageRequest.of(filter.getPage(), filter.getSize());
        Page<User> therapistsPage = userRepository.findAll(filter.getMongoPredicate(), pageRequest);
        return new PageableDto<>(therapistsPage, TherapistResponse::create);
    }

    @Override
    public TherapistResponse getTherapistByUsername(String username) {
        User therapist = userRepository.findById(username)
                .filter(user -> user.hasRole(Role.THERAPIST))
                .orElseThrow(() -> new ApiException(ApiErrorContainer.USER_NOT_FOUND));
        return TherapistResponse.create(therapist);
    }
}
