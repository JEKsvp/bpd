package com.jeksvp.goalkeeper.service.impl;

import com.jeksvp.goalkeeper.entity.Goal;
import com.jeksvp.goalkeeper.entity.User;
import com.jeksvp.goalkeeper.exceptions.ApiErrorContainer;
import com.jeksvp.goalkeeper.exceptions.ApiException;
import com.jeksvp.goalkeeper.repository.GoalRepository;
import com.jeksvp.goalkeeper.repository.UserRepository;
import com.jeksvp.goalkeeper.service.GoalService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    public GoalServiceImpl(GoalRepository goalRepository, UserRepository userRepository) {
        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Goal> findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ApiException(ApiErrorContainer.RESOURCE_NOT_FOUND));
        return goalRepository.findByUser(user);
    }

    @Override
    public List<Goal> findByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(ApiErrorContainer.RESOURCE_NOT_FOUND));
        return goalRepository.findByUser(user);
    }
}
