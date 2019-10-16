package com.jeksvp.goalkeeper.service.impl;

import com.jeksvp.goalkeeper.domain.entity.Role;
import com.jeksvp.goalkeeper.service.SignUpService;
import com.jeksvp.goalkeeper.web.dto.request.RegisterUserRequest;
import com.jeksvp.goalkeeper.web.dto.response.UserResponse;
import com.jeksvp.goalkeeper.domain.entity.User;
import com.jeksvp.goalkeeper.exceptions.ApiErrorContainer;
import com.jeksvp.goalkeeper.exceptions.ApiException;
import com.jeksvp.goalkeeper.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collections;

@Service
public class SignUpServiceImpl implements SignUpService {

    private final UserRepository userRepository;

    public SignUpServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse registerUser(RegisterUserRequest request) {
        validate(request);
        User user = new User(
                request.getUsername(),
                request.getPassword(),
                request.getEmail(),
                Collections.singletonList(Role.USER));
        User registeredUser = userRepository.save(user);
        return UserResponse.of(registeredUser);
    }

    private void validate(RegisterUserRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ApiException(ApiErrorContainer.VALIDATION_ERROR, MessageFormat.format("Username {0} exists", request.getUsername()));
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ApiException(ApiErrorContainer.VALIDATION_ERROR, MessageFormat.format("Email {0} exists", request.getEmail()));
        }
    }
}
