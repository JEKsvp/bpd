package com.jeksvp.goalkeeper.service;

import com.jeksvp.goalkeeper.entity.Goal;

import java.util.List;

public interface GoalService {

    List<Goal> findByUsername(String username);

    List<Goal> findByUserId(Long userId);
}
