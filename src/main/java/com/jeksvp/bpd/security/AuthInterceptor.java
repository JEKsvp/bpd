package com.jeksvp.bpd.security;

import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.security.filter.HttpRequestAccessResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final HttpRequestAccessResolver httpRequestAccessResolver;

    public AuthInterceptor(HttpRequestAccessResolver httpRequestAccessResolver) {
        this.httpRequestAccessResolver = httpRequestAccessResolver;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (httpRequestAccessResolver.hasAccess(request)) {
            return true;
        } else {
            throw new ApiException(ApiErrorContainer.FORBIDDEN);
        }
    }
}
