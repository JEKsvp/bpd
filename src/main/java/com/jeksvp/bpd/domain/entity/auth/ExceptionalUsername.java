package com.jeksvp.bpd.domain.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ExceptionalUsername {

    ANONYM("anonymousUser"),
    CLIENT_ID("clientid");

    private String username;
    private static final ExceptionalUsername[] values = values();

    public static boolean isUsernameExceptional(String username) {
        return Arrays.stream(values)
                .anyMatch(exceptionalUsername
                        -> exceptionalUsername.getUsername().equals(username));
    }

}
