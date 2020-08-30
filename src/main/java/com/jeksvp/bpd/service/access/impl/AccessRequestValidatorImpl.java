package com.jeksvp.bpd.service.access.impl;

import com.jeksvp.bpd.domain.entity.Role;
import com.jeksvp.bpd.domain.entity.User;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.repository.ClientAccessRepository;
import com.jeksvp.bpd.repository.UserRepository;
import com.jeksvp.bpd.service.access.AccessRequestValidator;
import com.jeksvp.bpd.utils.SecurityUtils;
import com.jeksvp.bpd.web.dto.request.access.AccessRequest;
import com.jeksvp.bpd.web.dto.request.access.AccessStatusRequest;
import org.springframework.stereotype.Component;

@Component
public class AccessRequestValidatorImpl implements AccessRequestValidator {

    private final UserRepository userRepository;
    private final ClientAccessRepository clientAccessRepository;

    public AccessRequestValidatorImpl(UserRepository userRepository,
                                      ClientAccessRepository clientAccessRepository) {
        this.userRepository = userRepository;
        this.clientAccessRepository = clientAccessRepository;
    }

    @Override
    public void validate(AccessRequest accessRequest) {
        User toUser = userRepository.findById(accessRequest.getUsername())
                .orElseThrow(() -> new ApiException(ApiErrorContainer.USER_NOT_FOUND));

        if (SecurityUtils.hasRole(Role.THERAPIST) && AccessStatusRequest.PENDING.equals(accessRequest.getStatus())) {
            throw new ApiException(ApiErrorContainer.USER_NOT_FOUND);
        }

        if (SecurityUtils.hasRole(Role.THERAPIST) && toUser.hasRole(Role.THERAPIST)) {
            throw new ApiException(ApiErrorContainer.USER_NOT_FOUND);
        }

        if (SecurityUtils.hasRole(Role.CLIENT) && toUser.hasRole(Role.CLIENT)) {
            throw new ApiException(ApiErrorContainer.USER_NOT_FOUND);
        }

        clientAccessRepository.findById(SecurityUtils.getCurrentUserName())
                .orElseThrow(() -> new ApiException(ApiErrorContainer.CLIENT_ACCESS_LIST_NOT_FOUND));
    }
}
