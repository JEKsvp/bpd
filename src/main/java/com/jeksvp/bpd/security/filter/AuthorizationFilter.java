package com.jeksvp.bpd.security.filter;

import com.jeksvp.bpd.domain.entity.auth.ExceptionalRoutes;
import com.jeksvp.bpd.domain.entity.auth.ExceptionalUsername;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String username = SecurityUtils.getCurrentUserName();
        String pathInfo = new UrlPathHelper().getRequestUri(httpServletRequest);
        if (!doesRequestUriValid(pathInfo, username)) {
            ApiErrorContainer error = ApiErrorContainer.FORBIDDEN;
            httpServletResponse.sendError(error.getHttpStatus().value(), error.getMessage());
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private boolean doesRequestUriValid(String pathInfo, String username) {
        if (ExceptionalRoutes.isRouteExceptional(pathInfo)) {
            return true;
        }
        if (StringUtils.isNotBlank(username)) {
            if (ExceptionalUsername.isUsernameExceptional(username)) {
                return true;
            }
            return pathInfo.contains(username);
        }
        return false;
    }
}
