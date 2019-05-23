package com.jeksvp.goalkeeper.controller;

import com.jeksvp.goalkeeper.web.dto.response.UserResponse;
import com.jeksvp.goalkeeper.service.UserService;
import com.jeksvp.goalkeeper.utils.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public UserResponse getUser(@PathVariable String username) {
        return userService.getUserByUserName(username);
    }

    @GetMapping("/current")
    public UserResponse getCurrentUser() {
        String currentUserName = SecurityUtils.getCurrentUserName();
        return userService.getUserByUserName(currentUserName);
    }
}
