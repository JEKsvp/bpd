package com.jeksvp.goalkeeper.service;

import com.jeksvp.goalkeeper.web.dto.response.UserResponse;

public interface UserService {

    UserResponse getUser(Long userId);

    UserResponse getUserByUserName(String username);
}
