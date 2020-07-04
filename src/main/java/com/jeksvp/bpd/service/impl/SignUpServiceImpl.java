package com.jeksvp.bpd.service.impl;

import com.jeksvp.bpd.domain.entity.Role;
import com.jeksvp.bpd.service.SignUpService;
import com.jeksvp.bpd.web.dto.request.RegisterUserRequest;
import com.jeksvp.bpd.web.dto.response.UserResponse;
import com.jeksvp.bpd.domain.entity.User;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collections;

@Service
public class SignUpServiceImpl implements SignUpService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public SignUpServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse registerUser(RegisterUserRequest request) {
        validate(request);
        User user = User.create(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
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
