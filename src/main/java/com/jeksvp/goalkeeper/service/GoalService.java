package com.jeksvp.goalkeeper.service;

import com.jeksvp.goalkeeper.dto.request.CreateGoalRequest;
import com.jeksvp.goalkeeper.dto.request.UpdateGoalRequest;
import com.jeksvp.goalkeeper.dto.response.GoalResponse;

import java.util.List;

public interface GoalService {

    List<GoalResponse> findAll();

    List<GoalResponse> findByUsername(String username);

    List<GoalResponse> findByUserId(Long userId);

    GoalResponse createGoal(CreateGoalRequest request);

    GoalResponse findById(Long id);

    GoalResponse updateGoal(Long goalId, UpdateGoalRequest request);

    void deleteById(Long goalId);
}
