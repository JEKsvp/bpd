package com.jeksvp.bpd.web.controller;

import com.jeksvp.bpd.service.SignUpService;
import com.jeksvp.bpd.web.dto.request.RegisterUserRequest;
import com.jeksvp.bpd.web.dto.response.UserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class SignUpController {

    private final SignUpService signUpService;

    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody RegisterUserRequest request) {
        return signUpService.registerUser(request);
    }
}
