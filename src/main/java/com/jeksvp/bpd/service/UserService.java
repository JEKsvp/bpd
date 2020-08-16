package com.jeksvp.bpd.service;

import com.jeksvp.bpd.web.dto.response.user.UserResponse;

public interface UserService {

    UserResponse getByUsername(String username);
}
