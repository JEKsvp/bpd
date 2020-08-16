package com.jeksvp.bpd.service.impl;

import com.jeksvp.bpd.service.SignUpInitializer;
import com.jeksvp.bpd.service.SignUpService;
import com.jeksvp.bpd.service.UnacceptableUsernameValidator;
import com.jeksvp.bpd.web.dto.creator.impl.UserCreator;
import com.jeksvp.bpd.web.dto.request.SignUpRequest;
import com.jeksvp.bpd.web.dto.response.user.UserResponse;
import com.jeksvp.bpd.domain.entity.User;
import com.jeksvp.bpd.exceptions.ApiErrorContainer;
import com.jeksvp.bpd.exceptions.ApiException;
import com.jeksvp.bpd.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
public class SignUpServiceImpl implements SignUpService {

    private final UserRepository userRepository;
    private final UserCreator userCreator;
    private final UnacceptableUsernameValidator unacceptableUsernameValidator;
    private final List<SignUpInitializer> signUpInitializers;

    public SignUpServiceImpl(UserRepository userRepository,
                             UserCreator userCreator,
                             UnacceptableUsernameValidator unacceptableUsernameValidator, List<SignUpInitializer> signUpInitializers) {
        this.userRepository = userRepository;
        this.userCreator = userCreator;
        this.unacceptableUsernameValidator = unacceptableUsernameValidator;
        this.signUpInitializers = signUpInitializers;
    }

    //todo do all this stuff in transaction
    @Override
    public UserResponse registerUser(SignUpRequest request) {
        validate(request);
        User user = userCreator.create(request);
        User registeredUser = userRepository.save(user);
        initializeUserEntities(user);
        return UserResponse.of(registeredUser);
    }

    private void initializeUserEntities(User user) {
        signUpInitializers.stream()
                .filter(initializer -> initializer.shouldCreate(user))
                .forEach(initializer -> initializer.createEntityFor(user));
    }

    private void validate(SignUpRequest request) {
        unacceptableUsernameValidator.validateUsername(request.getUsername());
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ApiException(ApiErrorContainer.VALIDATION_ERROR, MessageFormat.format("Username {0} exists", request.getUsername()));
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ApiException(ApiErrorContainer.VALIDATION_ERROR, MessageFormat.format("Email {0} exists", request.getEmail()));
        }
    }
}
