package com.jeksvp.goalkeeper.service;

import com.jeksvp.goalkeeper.entity.RandomCity;
import com.jeksvp.goalkeeper.entity.User;

import java.util.List;

/**
 * Created by nydiarra on 06/05/17.
 */
public interface GenericService {
    User findByUsername(String username);

    List<User> findAllUsers();

    List<RandomCity> findAllRandomCities();
}
