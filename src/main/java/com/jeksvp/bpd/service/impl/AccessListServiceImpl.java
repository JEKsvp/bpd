package com.jeksvp.bpd.service.impl;

import com.jeksvp.bpd.domain.entity.access.AccessList;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.repository.AccessListRepository;
import com.jeksvp.bpd.service.AccessListService;
import com.jeksvp.bpd.web.dto.request.access.AccessFilter;
import com.jeksvp.bpd.web.dto.response.access.AccessResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccessListServiceImpl implements AccessListService {

    private final AccessListRepository accessListRepository;

    public AccessListServiceImpl(AccessListRepository accessListRepository) {
        this.accessListRepository = accessListRepository;
    }

    @Override
    public List<AccessResponse> getAccessesOfUser(String username, AccessFilter filter) {
        return accessListRepository.findById(username)
                .orElseThrow(() -> new ApiException(ApiErrorContainer.CLIENT_ACCESS_LIST_NOT_FOUND))
                .getAccesses().stream()
                .filter(filter::passed)
                .map(AccessResponse::create)
                .collect(Collectors.toList());
    }

    @Override
    public void createAccessClientsList(String username) {
        accessListRepository.save(AccessList.create(username));
    }
}
