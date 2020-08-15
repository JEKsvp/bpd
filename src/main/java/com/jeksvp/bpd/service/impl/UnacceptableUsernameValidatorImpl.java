package com.jeksvp.bpd.service.impl;

import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.service.UnacceptableUsernameValidator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UnacceptableUsernameValidatorImpl implements UnacceptableUsernameValidator {

    private static final List<String> unacceptableUsernames = List.of(
            "current"
    );

    @Override
    public void validateUsername(String username) {
        boolean isUnacceptable = unacceptableUsernames.stream()
                .anyMatch(unavailableUsername -> unavailableUsername.equals(username.toLowerCase().strip()));
        if (isUnacceptable) {
            throw new ApiException(ApiErrorContainer.VALIDATION_ERROR, "Username is unacceptable");
        }
    }
}
