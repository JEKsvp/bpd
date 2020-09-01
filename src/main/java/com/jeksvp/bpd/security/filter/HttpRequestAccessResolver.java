package com.jeksvp.bpd.security.filter;

import com.jeksvp.bpd.domain.entity.access.AccessList;
import com.jeksvp.bpd.domain.entity.access.AccessStatus;
import com.jeksvp.bpd.domain.entity.auth.ExceptionalRoutes;
import com.jeksvp.bpd.domain.entity.auth.ExceptionalUsername;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.repository.AccessListRepository;
import com.jeksvp.bpd.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class HttpRequestAccessResolver {

    public static final String USERNAME = "username";

    private final AccessListRepository accessListRepository;

    public HttpRequestAccessResolver(AccessListRepository accessListRepository) {
        this.accessListRepository = accessListRepository;
    }

    public boolean hasAccess(HttpServletRequest request) {
        String pathInfo = new UrlPathHelper().getRequestUri(request);
        String currentUsername = SecurityUtils.getCurrentUserName();

        if (ExceptionalRoutes.isRouteExceptional(pathInfo)) {
            return true;
        }
        if (StringUtils.isNotBlank(currentUsername)) {
            if (ExceptionalUsername.isUsernameExceptional(currentUsername)) {
                return true;
            }
            String resourceOwnerUsername = extractResourceOwnerUsername(request);
            return currentUsername.equals(resourceOwnerUsername)
                    || (isGetHttpMethod(request) && hasAccessToResourceOwner(currentUsername, resourceOwnerUsername));
        }
        return false;
    }

    private boolean isGetHttpMethod(HttpServletRequest request) {
        return HttpMethod.GET.name().equals(request.getMethod());
    }

    private boolean hasAccessToResourceOwner(String currentUsername, String resourceOwnerUsername) {
        AccessList accessList = accessListRepository.findById(resourceOwnerUsername)
                .orElseThrow(() -> new ApiException(ApiErrorContainer.USER_NOT_FOUND));
        return accessList.hasAccessStatusFor(currentUsername, AccessStatus.ACCEPT);
    }

    private String extractResourceOwnerUsername(HttpServletRequest request) {
        Object attribute = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if (attribute instanceof Map) {
            Map<String, String> pathVariables = (Map<String, String>) attribute;
            return pathVariables.get(USERNAME);
        }
        return null;
    }
}
