package com.jeksvp.bpd.web.controller;

import com.jeksvp.bpd.service.UserService;
import com.jeksvp.bpd.utils.SecurityUtils;
import com.jeksvp.bpd.web.dto.response.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public UserResponse getUser(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/current")
    public UserResponse getCurrentUser() {
        String currentUserName = SecurityUtils.getCurrentUserName();
        return userService.getUserByUsername(currentUserName);
    }
}
