package com.jeksvp.goalkeeper.service.impl;

import com.jeksvp.goalkeeper.dto.request.CreateProgressRequest;
import com.jeksvp.goalkeeper.dto.request.UpdateProgressRequest;
import com.jeksvp.goalkeeper.dto.response.ProgressResponse;
import com.jeksvp.goalkeeper.entity.Goal;
import com.jeksvp.goalkeeper.entity.Progress;
import com.jeksvp.goalkeeper.exceptions.ApiErrorContainer;
import com.jeksvp.goalkeeper.exceptions.ApiException;
import com.jeksvp.goalkeeper.repository.GoalRepository;
import com.jeksvp.goalkeeper.repository.ProgressRepository;
import com.jeksvp.goalkeeper.service.ProgressService;
import com.jeksvp.goalkeeper.utils.FieldSetter;
import com.jeksvp.goalkeeper.utils.SecurityUtils;
import org.springframework.stereotype.Component;

@Component
public class ProgressServiceImpl implements ProgressService {

    private final ProgressRepository progressRepository;
    private final GoalRepository goalRepository;

    public ProgressServiceImpl(ProgressRepository progressRepository, GoalRepository goalRepository) {
        this.progressRepository = progressRepository;
        this.goalRepository = goalRepository;
    }

    @Override
    public ProgressResponse findById(Long id) {
        Progress progress = findProgressById(id);
        return ProgressResponse.of(progress);
    }

    @Override
    public ProgressResponse createProgress(CreateProgressRequest request) {
        Goal goal = goalRepository.findById(request.getGoalId()).orElseThrow(() -> new ApiException(ApiErrorContainer.RESOURCE_NOT_FOUND));
        Progress progress = Progress.of(request);
        progress.setGoal(goal);
        Progress newProgress = progressRepository.save(progress);
        return ProgressResponse.of(newProgress);
    }

    @Override
    public ProgressResponse updateProgress(Long progressId, UpdateProgressRequest request) {
        Progress progress = findProgressById(progressId);
        String username = progress.getGoal().getUser().getUsername();
        SecurityUtils.validateUserName(username);
        updateProgress(progress, request);
        return ProgressResponse.of(progress);
    }

    @Override
    public void deleteById(Long progressId) {
        Progress progress = findProgressById(progressId);
        String username = progress.getGoal().getUser().getUsername();
        SecurityUtils.validateUserName(username);
        progressRepository.deleteById(progressId);
    }

    private void updateProgress(Progress progress, UpdateProgressRequest request) {
        FieldSetter.setIfNotNull(progress::setMaxValue, request.getMaxValue());
        FieldSetter.setIfNotNull(progress::setCurrentValue, request.getCurrentValue());
    }

    private Progress findProgressById(Long progressId) {
        return progressRepository.findById(progressId).orElseThrow(() -> new ApiException(ApiErrorContainer.RESOURCE_NOT_FOUND));
    }
}
