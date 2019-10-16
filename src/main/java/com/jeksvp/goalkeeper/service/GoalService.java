package com.jeksvp.goalkeeper.service;

import com.jeksvp.goalkeeper.web.dto.request.GoalRequest;
import com.jeksvp.goalkeeper.web.dto.response.GoalResponse;

import java.util.List;

public interface GoalService {

    List<GoalResponse> findAll();

    List<GoalResponse> findByUsername(String username);

    GoalResponse createGoal(GoalRequest request);

    GoalResponse findById(String id);

    GoalResponse updateGoal(String goalId, GoalRequest request);

    void deleteById(String  goalId);
}
