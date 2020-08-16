package com.jeksvp.bpd.web.dto.response.user;

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
    private String firstName;
    private String lastName;
    private String aboutMe;

    public static UserResponse of(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .roles(user.getRoles())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .aboutMe(user.getAboutMe())
                .build();
    }
}
