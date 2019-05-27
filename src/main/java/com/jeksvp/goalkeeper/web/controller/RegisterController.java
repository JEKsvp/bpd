package com.jeksvp.goalkeeper.web.controller;

import com.jeksvp.goalkeeper.web.dto.request.RegisterUserRequest;
import com.jeksvp.goalkeeper.web.dto.response.UserResponse;
import com.jeksvp.goalkeeper.service.RegisterUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegisterController {

    private final RegisterUserService registerUserService;

    public RegisterController(RegisterUserService registerUserService) {
        this.registerUserService = registerUserService;
    }

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody RegisterUserRequest request) {
        return registerUserService.registerUser(request);
    }
}
