package com.jeksvp.bpd.domain.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ExceptionalRoutes {
    OATH_REQUEST("/oauth/token"),
    CURRENT_USER_REQUEST("/api/v1/users/current"),
    CURRENT_USER_ACCESSES_REQUEST("/api/v1/users/current/accesses"),
    SEND_ACCESS_REQUEST("/api/v1/access-request"),
    THERAPISTS_REQUEST("/api/v1/therapists"),
    THERAPIST_REQUEST("/api/v1/therapists/*"),
    SIGN_UP_REQUEST("/api/v1/signup");

    private String route;
    private static final ExceptionalRoutes[] routes = values();
    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

    public static boolean isRouteExceptional(String route) {
        return Arrays.stream(routes)
                .anyMatch(exceptionalRoute -> antPathMatcher.match(exceptionalRoute.getRoute(), route));
    }
}
