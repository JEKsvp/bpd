package com.jeksvp.goalkeeper.service.impl;

import com.jeksvp.goalkeeper.dto.request.RegisterUserRequest;
import com.jeksvp.goalkeeper.dto.response.UserResponse;
import com.jeksvp.goalkeeper.entity.Role;
import com.jeksvp.goalkeeper.entity.User;
import com.jeksvp.goalkeeper.exceptions.ApiErrorContainer;
import com.jeksvp.goalkeeper.exceptions.ApiException;
import com.jeksvp.goalkeeper.repository.RoleRepository;
import com.jeksvp.goalkeeper.repository.UserRepository;
import com.jeksvp.goalkeeper.service.RegisterUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

@Service
public class RegisterUserServiceImpl implements RegisterUserService {

    private static final String DEFAULT_ROLE_NAME = "USER";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public RegisterUserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse registerUser(RegisterUserRequest request) {
        validate(request);
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Role defaultRole = roleRepository.findByRoleName(DEFAULT_ROLE_NAME);
        user.setRoles(Collections.singletonList(defaultRole));
        User registeredUser = userRepository.save(user);
        return UserResponse.of(registeredUser);
    }

    private void validate(RegisterUserRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ApiException(ApiErrorContainer.VALIDATION_ERROR, "This username is taken by another user");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ApiException(ApiErrorContainer.VALIDATION_ERROR, "This email is taken by another user");
        }
    }
}
