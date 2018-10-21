package com.jeksvp.goalkeeper.service;

import com.jeksvp.goalkeeper.dto.request.RegisterUserRequest;
import com.jeksvp.goalkeeper.dto.response.UserResponse;

public interface RegisterUserService {

    UserResponse registerUser(RegisterUserRequest request);
}
