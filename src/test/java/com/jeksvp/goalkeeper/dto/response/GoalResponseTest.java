package com.jeksvp.goalkeeper.dto.response;

import com.jeksvp.goalkeeper.entity.Goal;
import com.jeksvp.goalkeeper.entity.Progress;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class GoalResponseTest {

    @Test
    public void ofGoalTest() {
        Goal goal = buildGoal();
        GoalResponse actual = GoalResponse.of(goal);
        assertEquals(buildExpectedGoalResponse(), actual);
    }

    @Test
    public void ofGoalListTest() {
        List<Goal> goals = new ArrayList<>(Arrays.asList(buildGoal(), buildGoal()));
        List<GoalResponse> actual = GoalResponse.of(goals);
        List<GoalResponse> expected = new ArrayList<>(Arrays.asList(buildExpectedGoalResponse(), buildExpectedGoalResponse()));
        assertEquals(expected, actual);
    }

    private Goal buildGoal() {
        Goal goal = new Goal();
        goal.setId(1L);
        goal.setName("test name");
        goal.setDescription("test description");
        Progress progress = new Progress();
        progress.setId(1L);
        progress.setMaxValue(22f);
        progress.setCurrentValue(12f);
        List<Progress> progresses = new ArrayList<>(Arrays.asList(progress, progress));
        goal.setProgresses(progresses);
        return goal;
    }

    private GoalResponse buildExpectedGoalResponse() {
        GoalResponse response = new GoalResponse();
        response.setId(1L);
        response.setName("test name");
        response.setDescription("test description");
        ProgressesResponse progressesResponse = new ProgressesResponse();
        progressesResponse.setId(1L);
        progressesResponse.setMaxValue(22f);
        progressesResponse.setCurrentValue(12f);
        List<ProgressesResponse> progressesResponses = new ArrayList<>(Arrays.asList(progressesResponse, progressesResponse));
        response.setProgresses(progressesResponses);
        return response;
    }
}