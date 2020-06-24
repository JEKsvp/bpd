package com.jeksvp.bpd.service;

import com.jeksvp.bpd.web.dto.response.UserResponse;

public interface UserService {

    UserResponse getUser(String  userId);

    UserResponse getUserByUsername(String username);
}
