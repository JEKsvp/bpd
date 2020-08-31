package com.jeksvp.bpd.utils;

import com.jeksvp.bpd.domain.entity.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

public class SecurityUtils {

    public static String getCurrentUserName() {
        SecurityContext context = SecurityContextHolder.getContext();
        return context.getAuthentication().getName();
    }

    public static List<SimpleGrantedAuthority> getCurrentAuthority() {
        SecurityContext context = SecurityContextHolder.getContext();
        return (List<SimpleGrantedAuthority>) context.getAuthentication().getAuthorities();
    }

    public static List<Role> getRoles() {
        SecurityContext context = SecurityContextHolder.getContext();
        List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) context.getAuthentication().getAuthorities();
        return authorities.stream()
                .map(grantedAuthority -> Role.valueOf(grantedAuthority.getAuthority()))
                .collect(Collectors.toList());
    }

    public static boolean hasRole(Role role) {
        SecurityContext context = SecurityContextHolder.getContext();
        List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) context.getAuthentication().getAuthorities();
        return authorities.stream()
                .anyMatch(grantedAuthority -> role.name().equals(grantedAuthority.getAuthority()));
    }

}
