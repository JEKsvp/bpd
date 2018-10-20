package com.jeksvp.goalkeeper.repository;

import com.jeksvp.goalkeeper.entity.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
