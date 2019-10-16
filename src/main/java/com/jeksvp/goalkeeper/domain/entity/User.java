package com.jeksvp.goalkeeper.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Document
@Builder
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class User {

    @Id
    private String id;
    private String username;
    private String password;
    private String email;
    private List<Role> roles = new ArrayList<>();

    User() {
    }

    public User(String username, String password, String email, List<Role> roles) {
        this.username = username;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
        this.email = email;
        this.roles = roles;
    }

}

