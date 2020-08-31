package com.jeksvp.bpd.service.access.impl;

import com.jeksvp.bpd.domain.entity.Role;
import com.jeksvp.bpd.domain.entity.User;
import com.jeksvp.bpd.domain.entity.access.AccessStatus;
import com.jeksvp.bpd.domain.entity.access.client.ClientAccessList;
import com.jeksvp.bpd.domain.entity.access.therapist.TherapistAccessList;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.repository.ClientAccessRepository;
import com.jeksvp.bpd.repository.TherapistAccessRepository;
import com.jeksvp.bpd.repository.UserRepository;
import com.jeksvp.bpd.service.access.AccessRequestValidator;
import com.jeksvp.bpd.utils.AccessStatusResolver;
import com.jeksvp.bpd.utils.SecurityUtils;
import com.jeksvp.bpd.web.dto.request.access.AccessRequest;
import com.jeksvp.bpd.web.dto.request.access.AccessStatusRequest;
import org.springframework.stereotype.Component;

@Component
public class AccessRequestValidatorImpl implements AccessRequestValidator {

    private final UserRepository userRepository;
    private final ClientAccessRepository clientAccessRepository;
    private final TherapistAccessRepository therapistAccessRepository;

    public AccessRequestValidatorImpl(UserRepository userRepository,
                                      ClientAccessRepository clientAccessRepository,
                                      TherapistAccessRepository therapistAccessRepository) {
        this.userRepository = userRepository;
        this.clientAccessRepository = clientAccessRepository;
        this.therapistAccessRepository = therapistAccessRepository;
    }

    @Override
    public void validate(AccessRequest accessRequest) {
        User toUser = userRepository.findById(accessRequest.getUsername())
                .orElseThrow(() -> new ApiException(ApiErrorContainer.USER_NOT_FOUND));


        if (SecurityUtils.hasRole(Role.THERAPIST) && toUser.hasRole(Role.THERAPIST)) {
            throw new ApiException(ApiErrorContainer.USER_NOT_FOUND);
        }

        if (SecurityUtils.hasRole(Role.CLIENT) && toUser.hasRole(Role.CLIENT)) {
            throw new ApiException(ApiErrorContainer.USER_NOT_FOUND);
        }

        if (SecurityUtils.hasRole(Role.THERAPIST) && AccessStatusRequest.PENDING.equals(accessRequest.getStatus())) {
            throw new ApiException(ApiErrorContainer.THERAPIST_CANT_PENDING_ACCESS_REQUEST);
        }

        if (SecurityUtils.hasRole(Role.CLIENT) && AccessStatusRequest.ACCEPT.equals(accessRequest.getStatus())) {
            throw new ApiException(ApiErrorContainer.CLIENT_CANT_ACCEPT_ACCESS_REQUEST);
        }
        validateStatus(toUser, accessRequest.getStatus());
    }

    private void validateStatus(User toUser, AccessStatusRequest status) {
        String fromUsername = SecurityUtils.getCurrentUserName();
        String toUsername = toUser.getUsername();
        AccessStatus accessStatus = AccessStatusResolver.resolve(status);
        if (!AccessStatus.PENDING.equals(accessStatus)) {
            if (toClient(toUser)) {
                checkRequestFromTherapistToClient(fromUsername, toUsername);
                checkRequestFromClientToTherapist(toUsername, fromUsername);
            } else {
                checkRequestFromClientToTherapist(fromUsername, toUsername);
                checkRequestFromTherapistToClient(toUsername, fromUsername);
            }
        }
    }

    private boolean toClient(User toUser) {
        return toUser.hasRole(Role.CLIENT);
    }

    private void checkRequestFromClientToTherapist(String client, String therapist) {
        ClientAccessList clientAccessList = clientAccessRepository.findById(client)
                .orElseThrow(() -> new ApiException(ApiErrorContainer.CLIENT_ACCESS_LIST_NOT_FOUND));
        clientAccessList.findAccess(therapist)
                .filter(clientAccess -> AccessStatus.PENDING.equals(clientAccess.getStatus()))
                .orElseThrow(() -> new ApiException(ApiErrorContainer.THERE_IS_NO_PENDING_REQUEST));
    }

    private void checkRequestFromTherapistToClient(String therapist, String client) {
        TherapistAccessList therapistAccessList = therapistAccessRepository.findById(therapist)
                .orElseThrow(() -> new ApiException(ApiErrorContainer.CLIENT_ACCESS_LIST_NOT_FOUND));
        therapistAccessList.findAccess(client)
                .filter(therapistAccess -> AccessStatus.PENDING.equals(therapistAccess.getStatus()))
                .orElseThrow(() -> new ApiException(ApiErrorContainer.THERE_IS_NO_PENDING_REQUEST));
    }
}
