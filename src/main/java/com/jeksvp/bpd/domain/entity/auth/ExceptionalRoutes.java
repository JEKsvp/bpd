package com.jeksvp.bpd.domain.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ExceptionalRoutes {
    OATH_REQUEST("/oauth/token"),
    CURRENT_USER_REQUEST("/api/v1/users/current"),
    CURRENT_USER_THERAPISTS_REQUEST("/api/v1/users/current/therapists"),
    CURRENT_USER_CLIENTS_REQUEST("/api/v1/users/current/clients"),
    SIGN_UP_REQUEST("/api/v1/signup");

    private String route;
    private static final ExceptionalRoutes[] routes = values();

    public static boolean isRouteExceptional(String route) {
        return Arrays.stream(routes)
                .anyMatch(exceptionalUsername -> exceptionalUsername.getRoute().equals(route));
    }
}
