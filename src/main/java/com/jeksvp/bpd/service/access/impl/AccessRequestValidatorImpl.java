package com.jeksvp.bpd.service.access.impl;

import com.jeksvp.bpd.domain.entity.Role;
import com.jeksvp.bpd.domain.entity.User;
import com.jeksvp.bpd.domain.entity.access.AccessStatus;
import com.jeksvp.bpd.domain.entity.access.AccessList;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.repository.AccessListRepository;
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
    private final AccessListRepository accessListRepository;

    public AccessRequestValidatorImpl(UserRepository userRepository,
                                      AccessListRepository accessListRepository) {
        this.userRepository = userRepository;
        this.accessListRepository = accessListRepository;
    }

    @Override
    public void validate(AccessRequest accessRequest) {
        User toUser = userRepository.findById(accessRequest.getUsername())
                .orElseThrow(() -> new ApiException(ApiErrorContainer.USER_NOT_FOUND));

        if (SecurityUtils.hasRole(Role.THERAPIST)) {
            validateAsTherapist(accessRequest, toUser);
        }

        if (SecurityUtils.hasRole(Role.CLIENT)) {
            validateAsClient(accessRequest, toUser);
        }

        validateStatus(toUser, accessRequest.getStatus());
    }

    private void validateAsTherapist(AccessRequest accessRequest, User toUser) {
        if (toUser.hasRole(Role.THERAPIST)) {
            throw new ApiException(ApiErrorContainer.USER_NOT_FOUND);
        }

        if (AccessStatusRequest.PENDING.equals(accessRequest.getStatus())) {
            throw new ApiException(ApiErrorContainer.THERAPIST_CANT_PENDING_ACCESS_REQUEST);
        }
    }

    private void validateAsClient(AccessRequest accessRequest, User toUser) {
        if (toUser.hasRole(Role.CLIENT)) {
            throw new ApiException(ApiErrorContainer.USER_NOT_FOUND);
        }
        if (AccessStatusRequest.ACCEPT.equals(accessRequest.getStatus())) {
            throw new ApiException(ApiErrorContainer.CLIENT_CANT_ACCEPT_ACCESS_REQUEST);
        }

        AccessList accessList = accessListRepository.findById(SecurityUtils.getCurrentUserName())
                .orElseThrow(() -> new ApiException(ApiErrorContainer.CLIENT_ACCESS_LIST_NOT_FOUND));

        if (hasAnotherTherapist(toUser, accessList) || pendingToCurrentTherapist(toUser, accessList, accessRequest.getStatus())) {
            throw new ApiException(ApiErrorContainer.CLIENT_ALREADY_HAS_THERAPIST);
        }
    }

    private boolean hasAnotherTherapist(User toUser, AccessList accessList) {
        return !accessList.getAccesses().isEmpty() && !accessList.hasAccessStatusFor(toUser.getUsername());
    }

    private boolean pendingToCurrentTherapist(User toUser, AccessList accessList, AccessStatusRequest status) {
        return accessList.hasAccessStatusFor(toUser.getUsername()) && AccessStatusRequest.PENDING.equals(status);
    }

    private void validateStatus(User toUser, AccessStatusRequest status) {
        String fromUsername = SecurityUtils.getCurrentUserName();
        String toUsername = toUser.getUsername();
        AccessStatus accessStatus = AccessStatusResolver.resolve(status);
        if (AccessStatus.ACCEPT.equals(accessStatus)) {
            checkAccessStatusRequest(fromUsername, toUsername);
            checkAccessStatusRequest(toUsername, fromUsername);
        }
        if (AccessStatus.DECLINE.equals(accessStatus)) {
            isAccessStatusExists(fromUsername, toUsername);
            isAccessStatusExists(toUsername, fromUsername);
        }
    }

    private void isAccessStatusExists(String from, String to) {
        AccessList accessList = accessListRepository.findById(from)
                .orElseThrow(() -> new ApiException(ApiErrorContainer.CLIENT_ACCESS_LIST_NOT_FOUND));
        if (!accessList.hasAccessStatusFor(to)) {
            throw new ApiException(ApiErrorContainer.THERE_IS_NO_EXISTED_ACCESS_REQUEST);
        }
    }

    private void checkAccessStatusRequest(String from, String to) {
        AccessList accessList = accessListRepository.findById(from)
                .orElseThrow(() -> new ApiException(ApiErrorContainer.CLIENT_ACCESS_LIST_NOT_FOUND));
        accessList.findAccess(to)
                .filter(clientAccess -> AccessStatus.PENDING.equals(clientAccess.getStatus()))
                .orElseThrow(() -> new ApiException(ApiErrorContainer.THERE_IS_NO_PENDING_REQUEST));
    }
}
