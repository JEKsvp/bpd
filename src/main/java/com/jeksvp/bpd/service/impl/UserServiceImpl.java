package com.jeksvp.bpd.service.impl;

import com.jeksvp.bpd.web.dto.response.UserResponse;
import com.jeksvp.bpd.domain.entity.User;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.repository.UserRepository;
import com.jeksvp.bpd.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse getUser(String  userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ApiErrorContainer.RESOURCE_NOT_FOUND));
        return UserResponse.of(user);
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(ApiErrorContainer.RESOURCE_NOT_FOUND));
        return UserResponse.of(user);
    }
}
