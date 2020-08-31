package com.jeksvp.bpd.security.filter;

import com.jeksvp.bpd.domain.entity.auth.ExceptionalRoutes;
import com.jeksvp.bpd.domain.entity.auth.ExceptionalUsername;
import com.jeksvp.bpd.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;

@Component
public class HttpRequestAccessResolver {

    public boolean hasAccess(HttpServletRequest request) {
        String pathInfo = new UrlPathHelper().getRequestUri(request);
        String username = SecurityUtils.getCurrentUserName();

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
