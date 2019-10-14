package com.jeksvp.goalkeeper.service.impl;

import com.jeksvp.goalkeeper.web.dto.response.UserResponse;
import com.jeksvp.goalkeeper.domain.entity.User;
import com.jeksvp.goalkeeper.exceptions.ApiErrorContainer;
import com.jeksvp.goalkeeper.exceptions.ApiException;
import com.jeksvp.goalkeeper.repository.UserRepository;
import com.jeksvp.goalkeeper.service.UserService;
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
    public UserResponse getUserByUserName(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(ApiErrorContainer.RESOURCE_NOT_FOUND));
        return UserResponse.of(user);
    }
}
