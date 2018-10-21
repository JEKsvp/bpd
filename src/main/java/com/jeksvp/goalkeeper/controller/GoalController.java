package com.jeksvp.goalkeeper.controller;

import com.jeksvp.goalkeeper.dto.request.CreateGoalRequest;
import com.jeksvp.goalkeeper.dto.response.GoalResponse;
import com.jeksvp.goalkeeper.service.GoalService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/goals")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping
    public List<GoalResponse> getGoals(@RequestParam(required = false) String username) {
        if (username != null) {
            return goalService.findByUsername(username);
        }
        return goalService.findAll();
    }

    @PostMapping
    public GoalResponse createGoal(@RequestBody CreateGoalRequest request) {
        return goalService.createGoal(request);
    }
}
