package com.jeksvp.bpd.security.filter;

import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFilter extends OncePerRequestFilter {

    private final HttpRequestAccessResolver httpRequestAccessResolver;

    public AuthorizationFilter(HttpRequestAccessResolver httpRequestAccessResolver) {
        this.httpRequestAccessResolver = httpRequestAccessResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (httpRequestAccessResolver.hasAccess(httpServletRequest)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            ApiErrorContainer error = ApiErrorContainer.FORBIDDEN;
            httpServletResponse.sendError(error.getHttpStatus().value(), error.getMessage());
        }
    }
}
