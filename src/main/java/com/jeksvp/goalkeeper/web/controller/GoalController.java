package com.jeksvp.goalkeeper.web.controller;

import com.jeksvp.goalkeeper.web.dto.request.CreateGoalRequest;
import com.jeksvp.goalkeeper.web.dto.request.UpdateGoalRequest;
import com.jeksvp.goalkeeper.web.dto.response.GoalResponse;
import com.jeksvp.goalkeeper.service.GoalService;
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

    @GetMapping("/{goalId}")
    public GoalResponse getGoal(@PathVariable Long goalId) {
        return goalService.findById(goalId);
    }

    @PutMapping("/{goalId}")
    public GoalResponse updateGoal(@PathVariable Long goalId, @RequestBody UpdateGoalRequest request) {
        return goalService.updateGoal(goalId, request);
    }

    @DeleteMapping("/{goalId}")
    public void deleteGoal(@PathVariable Long goalId){
        goalService.deleteById(goalId);
    }
}
