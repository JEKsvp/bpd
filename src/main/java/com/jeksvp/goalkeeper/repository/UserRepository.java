package com.jeksvp.goalkeeper.repository;

import com.jeksvp.goalkeeper.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by nydiarra on 06/05/17.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
