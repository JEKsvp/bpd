package com.jeksvp.goalkeeper.service.impl;

import com.jeksvp.goalkeeper.dto.request.CreateGoalRequest;
import com.jeksvp.goalkeeper.dto.request.UpdateGoalRequest;
import com.jeksvp.goalkeeper.dto.response.GoalResponse;
import com.jeksvp.goalkeeper.entity.Goal;
import com.jeksvp.goalkeeper.entity.User;
import com.jeksvp.goalkeeper.exceptions.ApiErrorContainer;
import com.jeksvp.goalkeeper.exceptions.ApiException;
import com.jeksvp.goalkeeper.repository.GoalRepository;
import com.jeksvp.goalkeeper.repository.UserRepository;
import com.jeksvp.goalkeeper.service.GoalService;
import com.jeksvp.goalkeeper.utils.FieldSetter;
import com.jeksvp.goalkeeper.utils.SecurityUtils;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    public List<GoalResponse> findAll() {
        List<Goal> goals = goalRepository.findAll();
        return GoalResponse.of(goals);
    }

    @Override
    public List<GoalResponse> findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ApiException(ApiErrorContainer.RESOURCE_NOT_FOUND));
        List<Goal> goals = goalRepository.findByUser(user);
        return GoalResponse.of(goals);
    }

    @Override
    public List<GoalResponse> findByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(ApiErrorContainer.RESOURCE_NOT_FOUND));
        List<Goal> goals = goalRepository.findByUser(user);
        return GoalResponse.of(goals);
    }

    @Override
    @Transactional
    public GoalResponse createGoal(CreateGoalRequest request) {
        String currentUserName = SecurityUtils.getCurrentUserName();
        User user = userRepository.findByUsername(currentUserName).orElseThrow(() -> new ApiException(ApiErrorContainer.RESOURCE_NOT_FOUND));
        Goal newGoal = Goal.of(request);
        newGoal.setUser(user);
        newGoal.setCreateDate(LocalDateTime.now());
        Goal savedGoal = goalRepository.save(newGoal);
        return GoalResponse.of(savedGoal);
    }

    @Override
    public GoalResponse findById(Long id) {
        Goal goal = goalRepository.findById(id).orElseThrow(() -> new ApiException(ApiErrorContainer.RESOURCE_NOT_FOUND));
        return GoalResponse.of(goal);
    }

    @Override
    public GoalResponse updateGoal(Long goalId, UpdateGoalRequest request) {
        Goal goal = goalRepository.findById(goalId).orElseThrow(() -> new ApiException(ApiErrorContainer.RESOURCE_NOT_FOUND));
        updateGoal(goal, request);
        return GoalResponse.of(goal);
    }

    @Override
    public void deleteById(Long goalId) {
        goalRepository.deleteById(goalId);
    }

    private void updateGoal(Goal goal, UpdateGoalRequest request) {
        FieldSetter.setIfNotNull(goal::setName, request.getName());
        FieldSetter.setIfNotNull(goal::setDescription, request.getDescription());
        FieldSetter.setIfNotNull(goal::setExpirationDate, request.getExpirationDate());
    }
}
