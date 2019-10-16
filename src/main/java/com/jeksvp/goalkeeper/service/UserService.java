package com.jeksvp.goalkeeper.service;

import com.jeksvp.goalkeeper.web.dto.response.UserResponse;

public interface UserService {

    UserResponse getUser(String  userId);

    UserResponse getUserByUsername(String username);
}
