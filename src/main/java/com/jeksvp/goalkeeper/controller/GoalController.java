package com.jeksvp.goalkeeper.controller;

import com.jeksvp.goalkeeper.entity.Goal;
import com.jeksvp.goalkeeper.service.GoalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/goals")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping
    public List<Goal> getGoals(@RequestParam Long userId) {
        return goalService.findByUserId(userId);
    }
}
