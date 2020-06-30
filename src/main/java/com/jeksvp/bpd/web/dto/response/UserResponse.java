package com.jeksvp.bpd.web.dto.response;

import com.jeksvp.bpd.domain.entity.Role;
import com.jeksvp.bpd.domain.entity.User;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String username;
    private String email;
    private List<Role> roles;

    public static UserResponse of(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .roles(user.getRoles())
                .build();
    }
}
