package com.jeksvp.goalkeeper.utils;

import com.jeksvp.goalkeeper.exceptions.ApiErrorContainer;
import com.jeksvp.goalkeeper.exceptions.ApiException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class SecurityUtils {

    public static String getCurrentUserName() {
        SecurityContext context = SecurityContextHolder.getContext();
        return context.getAuthentication().getName();
    }

    public static List<SimpleGrantedAuthority> getCurrentAuthority() {
        SecurityContext context = SecurityContextHolder.getContext();
        return (List<SimpleGrantedAuthority>) context.getAuthentication().getAuthorities();
    }

    public static void validateUsername(String username) {
        if (!SecurityUtils.getCurrentUserName().equals(username)) {
            throw new ApiException(ApiErrorContainer.ACCESS_DENIED);
        }
    }
}
