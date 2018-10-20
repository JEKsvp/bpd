package com.jeksvp.goalkeeper.service.impl;

import com.jeksvp.goalkeeper.entity.RandomCity;
import com.jeksvp.goalkeeper.entity.User;
import com.jeksvp.goalkeeper.repository.RandomCityRepository;
import com.jeksvp.goalkeeper.repository.UserRepository;
import com.jeksvp.goalkeeper.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by nydiarra on 07/05/17.
 */
@Service
public class GenericServiceImpl implements GenericService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RandomCityRepository randomCityRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAllUsers() {
        return (List<User>)userRepository.findAll();
    }

    @Override
    public List<RandomCity> findAllRandomCities() {
        return (List<RandomCity>)randomCityRepository.findAll();
    }
}
