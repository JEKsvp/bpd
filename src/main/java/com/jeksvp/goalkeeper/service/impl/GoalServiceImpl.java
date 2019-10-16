package com.jeksvp.goalkeeper.service.impl;

import com.jeksvp.goalkeeper.domain.entity.Progress;
import com.jeksvp.goalkeeper.web.dto.mapper.ProgressRequestMapper;
import com.jeksvp.goalkeeper.web.dto.request.GoalRequest;
import com.jeksvp.goalkeeper.web.dto.request.ProgressRequest;
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

import java.util.List;
import java.util.stream.Collectors;

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
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(ApiErrorContainer.USER_NOT_FOUND));

        List<Goal> goals = goalRepository.findByUsername(user.getUsername());
        return GoalResponse.of(goals);
    }

    @Override
    public GoalResponse createGoal(GoalRequest request) {
        String currentUserName = SecurityUtils.getCurrentUserName();
        User user = userRepository.findByUsername(currentUserName)
                .orElseThrow(() -> new ApiException(ApiErrorContainer.USER_NOT_FOUND));

        Goal goal = new Goal(
                request.getName(),
                request.getDescription(),
                user.getUsername(),
                request.getExpirationDate());

        goal.setProgresses(new ProgressRequestMapper().map(request.getProgresses()));

        Goal savedGoal = goalRepository.save(goal);
        return GoalResponse.of(savedGoal);
    }

    @Override
    public GoalResponse findById(String id) {
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new ApiException(ApiErrorContainer.RESOURCE_NOT_FOUND));

        return GoalResponse.of(goal);
    }

    @Override
    public GoalResponse updateGoal(String goalId, GoalRequest request) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new ApiException(ApiErrorContainer.RESOURCE_NOT_FOUND));

        SecurityUtils.validateUsername(goal.getUsername());
        goal.setName(request.getName());
        goal.setDescription(request.getDescription());
        goal.setExpirationDate(request.getExpirationDate());
        goal.setProgresses(new ProgressRequestMapper().map(request.getProgresses()));
        goalRepository.save(goal);
        return GoalResponse.of(goal);
    }

    @Override
    public void deleteById(String goalId) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new ApiException(ApiErrorContainer.RESOURCE_NOT_FOUND));
        SecurityUtils.validateUsername(goal.getUsername());

        SecurityUtils.validateUsername(goal.getUsername());
        goalRepository.deleteById(goalId);
    }
}
