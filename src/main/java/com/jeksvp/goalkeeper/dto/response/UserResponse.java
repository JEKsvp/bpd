package com.jeksvp.goalkeeper.dto.response;

import com.jeksvp.goalkeeper.entity.User;
import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private String username;
    private String email;


    public static UserResponse of(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        return userResponse;
    }
}
