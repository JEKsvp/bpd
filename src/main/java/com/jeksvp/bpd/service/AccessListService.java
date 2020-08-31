package com.jeksvp.bpd.service;

import com.jeksvp.bpd.web.dto.request.access.AccessFilter;
import com.jeksvp.bpd.web.dto.response.access.AccessResponse;

import java.util.List;

public interface AccessListService {

    List<AccessResponse> getAccessesOfUser(String username, AccessFilter filter);

    void createAccessClientsList(String username);
}
