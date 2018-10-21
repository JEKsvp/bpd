package com.jeksvp.goalkeeper.repository;

import com.jeksvp.goalkeeper.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
