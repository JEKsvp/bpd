package com.jeksvp.goalkeeper.dto.request;

import lombok.Data;

@Data
public class RegisterUserRequest {

    private String username;
    private String password;
    private String email;
}
