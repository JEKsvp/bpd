package com.jeksvp.goalkeeper.service;

import com.jeksvp.goalkeeper.web.dto.request.RegisterUserRequest;
import com.jeksvp.goalkeeper.web.dto.response.UserResponse;

public interface RegisterUserService {

    UserResponse registerUser(RegisterUserRequest request);
}
