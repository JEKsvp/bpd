package com.jeksvp.goalkeeper.web.dto.response;

import com.jeksvp.goalkeeper.domain.entity.Role;
import com.jeksvp.goalkeeper.domain.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
public class UserResponse {

    private String id;
    private String username;
    private String email;
    private List<Role> roles;

    public static UserResponse of(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .roles(user.getRoles())
                .build();
    }
}
