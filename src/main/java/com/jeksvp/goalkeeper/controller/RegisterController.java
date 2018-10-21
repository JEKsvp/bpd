package com.jeksvp.goalkeeper.controller;

import com.jeksvp.goalkeeper.dto.request.RegisterUserRequest;
import com.jeksvp.goalkeeper.dto.response.UserResponse;
import com.jeksvp.goalkeeper.service.RegisterUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    private final RegisterUserService registerUserService;

    public RegisterController(RegisterUserService registerUserService) {
        this.registerUserService = registerUserService;
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterUserRequest request) {
        return registerUserService.registerUser(request);
    }
}
