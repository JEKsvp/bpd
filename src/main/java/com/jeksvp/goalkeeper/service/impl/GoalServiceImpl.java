package com.jeksvp.goalkeeper.service.impl;

import com.jeksvp.goalkeeper.domain.entity.Progress;
import com.jeksvp.goalkeeper.web.dto.request.CreateGoalRequest;
import com.jeksvp.goalkeeper.web.dto.request.UpdateGoalRequest;
import com.jeksvp.goalkeeper.web.dto.response.GoalResponse;
import com.jeksvp.goalkeeper.domain.entity.Goal;
import com.jeksvp.goalkeeper.domain.entity.User;
import com.jeksvp.goalkeeper.exceptions.ApiErrorContainer;
import com.jeksvp.goalkeeper.exceptions.ApiException;
import com.jeksvp.goalkeeper.repository.GoalRepository;
import com.jeksvp.goalkeeper.repository.UserRepository;
import com.jeksvp.goalkeeper.service.GoalService;
import com.jeksvp.goalkeeper.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        List<Goal> goals = goalRepository.findByUsername(user.getUsername());
        return GoalResponse.of(goals);
    }

    @Override
    public List<GoalResponse> findByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(ApiErrorContainer.RESOURCE_NOT_FOUND));
        List<Goal> goals = goalRepository.findByUsername(user.getUsername());
        return GoalResponse.of(goals);
    }

    @Override
    @Transactional
    public GoalResponse createGoal(CreateGoalRequest request) {
        String currentUserName = SecurityUtils.getCurrentUserName();
        User user = userRepository.findByUsername(currentUserName)
                .orElseThrow(() -> new ApiException(ApiErrorContainer.RESOURCE_NOT_FOUND));

        Goal goal = new Goal(request.getName(), request.getDescription(), user.getUsername(), request.getExpirationDate());
        request.getProgresses()
                .forEach(progress -> goal.addProgress(new Progress(progress.getMaxValue())));
        Goal savedGoal = goalRepository.save(goal);
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
        SecurityUtils.validateUserId(goal.getUsername());
        goal.update(request.getName(), request.getDescription(), request.getExpirationDate());
        return GoalResponse.of(goal);
    }

    @Override
    public void deleteById(Long goalId) {
        Goal goal = goalRepository.findById(goalId).orElseThrow(() -> new ApiException(ApiErrorContainer.RESOURCE_NOT_FOUND));
        SecurityUtils.validateUserId(goal.getUsername());
        goalRepository.deleteById(goalId);
    }
}
