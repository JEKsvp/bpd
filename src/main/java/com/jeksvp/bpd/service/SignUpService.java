package com.jeksvp.bpd.service;

import com.jeksvp.bpd.web.dto.request.RegisterUserRequest;
import com.jeksvp.bpd.web.dto.response.UserResponse;

public interface SignUpService {

    UserResponse registerUser(RegisterUserRequest request);
}
