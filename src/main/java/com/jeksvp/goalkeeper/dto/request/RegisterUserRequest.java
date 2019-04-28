package com.jeksvp.goalkeeper.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegisterUserRequest {

    @Size(min = 1, max = 20)
    private String username;

    @Size(min = 6, max = 30)
    private String password;

    @NotBlank
    private String email;
}
