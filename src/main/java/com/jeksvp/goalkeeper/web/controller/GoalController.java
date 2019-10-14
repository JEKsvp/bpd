package com.jeksvp.goalkeeper.web.controller;

import com.jeksvp.goalkeeper.web.dto.request.GoalRequest;
import com.jeksvp.goalkeeper.web.dto.response.GoalResponse;
import com.jeksvp.goalkeeper.service.GoalService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public GoalResponse createGoal(@Valid @RequestBody GoalRequest request) {
        return goalService.createGoal(request);
    }

    @GetMapping(value = "/{goalId}")
    public GoalResponse getGoal(@PathVariable String goalId) {
        return goalService.findById(goalId);
    }

    @PutMapping("/{goalId}")
    public GoalResponse updateGoal(@PathVariable String goalId, @RequestBody GoalRequest request) {
        return goalService.updateGoal(goalId, request);
    }

    @DeleteMapping("/{goalId}")
    public void deleteGoal(@PathVariable String goalId) {
        goalService.deleteById(goalId);
    }
}
