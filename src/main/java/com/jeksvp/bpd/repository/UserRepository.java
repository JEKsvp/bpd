package com.jeksvp.bpd.repository;

import com.jeksvp.bpd.domain.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface UserRepository extends MongoRepository<User, String > {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
