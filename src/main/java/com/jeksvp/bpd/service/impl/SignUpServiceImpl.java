package com.jeksvp.bpd.service.impl;

import com.jeksvp.bpd.domain.entity.Role;
import com.jeksvp.bpd.service.DiaryService;
import com.jeksvp.bpd.service.SignUpService;
import com.jeksvp.bpd.web.dto.request.SignUpRequest;
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
    private final DiaryService diaryService;

    public SignUpServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, DiaryService diaryService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.diaryService = diaryService;
    }

    @Override
    public UserResponse registerUser(SignUpRequest request) {
        validate(request);
        User user = User.create(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getEmail(),
                Collections.singletonList(request.getRole()),
                request.getFirstName(),
                request.getLastName(),
                request.getAboutMe()
        );
        User registeredUser = userRepository.save(user);

        //todo do in transaction
        if (Role.PATIENT.equals(request.getRole())) {
            diaryService.createDiary(registeredUser.getUsername());
        }
        return UserResponse.of(registeredUser);
    }

    private void validate(SignUpRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ApiException(ApiErrorContainer.VALIDATION_ERROR, MessageFormat.format("Username {0} exists", request.getUsername()));
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ApiException(ApiErrorContainer.VALIDATION_ERROR, MessageFormat.format("Email {0} exists", request.getEmail()));
        }
    }
}
