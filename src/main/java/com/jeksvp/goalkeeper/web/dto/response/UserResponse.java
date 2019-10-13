package com.jeksvp.goalkeeper.web.dto.response;

import com.jeksvp.goalkeeper.domain.entity.Role;
import com.jeksvp.goalkeeper.domain.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserResponse {

    private String id;
    private String username;
    private String email;
    private List<Role> roles;


    public static UserResponse of(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setRoles(user.getRoles());
        return userResponse;
    }
}
