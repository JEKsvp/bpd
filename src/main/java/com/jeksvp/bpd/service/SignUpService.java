package com.jeksvp.bpd.service;

import com.jeksvp.bpd.web.dto.request.SignUpRequest;
import com.jeksvp.bpd.web.dto.response.user.UserResponse;

public interface SignUpService {

    UserResponse registerUser(SignUpRequest request);
}
