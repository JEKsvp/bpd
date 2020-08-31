package com.jeksvp.bpd.web.dto.creator;

import com.jeksvp.bpd.domain.entity.User;
import com.jeksvp.bpd.support.Creator;
import com.jeksvp.bpd.web.dto.request.SignUpRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserCreator implements Creator<SignUpRequest, User> {

    private final BCryptPasswordEncoder passwordEncoder;

    public UserCreator(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User create(SignUpRequest request) {
        return User.create(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getEmail(),
                Collections.singletonList(request.getRole()),
                request.getFirstName(),
                request.getLastName(),
                request.getAboutMe()
        );
    }
}
