package com.jeksvp.bpd.service.impl;

import com.jeksvp.bpd.domain.entity.QUser;
import com.jeksvp.bpd.domain.entity.Role;
import com.jeksvp.bpd.domain.entity.User;
import com.jeksvp.bpd.domain.entity.access.therapist.TherapistAccessList;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.repository.TherapistAccessRepository;
import com.jeksvp.bpd.repository.UserRepository;
import com.jeksvp.bpd.service.TherapistService;
import com.jeksvp.bpd.web.dto.request.therapist.TherapistPageableFilter;
import com.jeksvp.bpd.web.dto.response.paging.PageableDto;
import com.jeksvp.bpd.web.dto.response.therapist.TherapistAccessResponse;
import com.jeksvp.bpd.web.dto.response.therapist.TherapistResponse;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TherapistServiceImpl implements TherapistService {

    private final TherapistAccessRepository therapistAccessRepository;
    private final UserRepository userRepository;

    public TherapistServiceImpl(TherapistAccessRepository therapistAccessRepository,
                                UserRepository userRepository) {
        this.therapistAccessRepository = therapistAccessRepository;
        this.userRepository = userRepository;
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

    @Override
    public PageableDto<TherapistResponse> getTherapists(TherapistPageableFilter filter) {
        PageRequest pageRequest = PageRequest.of(filter.getPage(), filter.getSize());
        Page<User> therapistsPage = userRepository.findAll(filter.getMongoPredicate(), pageRequest);
        return new PageableDto<>(therapistsPage, TherapistResponse::create);
    }
}
