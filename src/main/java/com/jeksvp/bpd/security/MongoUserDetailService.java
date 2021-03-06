package com.jeksvp.bpd.security;

import com.jeksvp.bpd.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MongoUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public MongoUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String loginCredentialName) throws UsernameNotFoundException {
        return userRepository.findByUsername(loginCredentialName)
                .or(() -> userRepository.findByEmail(loginCredentialName))
                .orElseThrow(() -> new UsernameNotFoundException(String.format("The username %s doesn't exist", loginCredentialName)));
    }
}
