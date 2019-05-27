package com.jeksvp.goalkeeper.service.impl;

import com.jeksvp.goalkeeper.web.dto.request.RegisterUserRequest;
import com.jeksvp.goalkeeper.web.dto.response.UserResponse;
import com.jeksvp.goalkeeper.domain.entity.Role;
import com.jeksvp.goalkeeper.domain.entity.User;
import com.jeksvp.goalkeeper.exceptions.ApiErrorContainer;
import com.jeksvp.goalkeeper.exceptions.ApiException;
import com.jeksvp.goalkeeper.repository.RoleRepository;
import com.jeksvp.goalkeeper.repository.UserRepository;
import com.jeksvp.goalkeeper.service.RegisterUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RegisterUserServiceImpl implements RegisterUserService {

    private static final String DEFAULT_ROLE_NAME = "USER";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public RegisterUserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserResponse registerUser(RegisterUserRequest request) {
        validate(request);
        Role defaultRole = roleRepository.findByRoleName(DEFAULT_ROLE_NAME);
        User user = new User(request.getUsername(), request.getPassword(), request.getEmail(), defaultRole);
        User registeredUser = userRepository.save(user);
        return UserResponse.of(registeredUser);
    }

    private void validate(RegisterUserRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ApiException(ApiErrorContainer.VALIDATION_ERROR, "This username exists");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ApiException(ApiErrorContainer.VALIDATION_ERROR, "This email exists");
        }
    }
}
