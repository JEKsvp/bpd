package com.jeksvp.goalkeeper.web.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeksvp.goalkeeper.domain.entity.Goal;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class GoalResponse {
    private Long id;
    private String name;
    private String description;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime createDate;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime expirationDate;

    private List<ProgressResponse> progresses;

    public static GoalResponse of(Goal goal) {
        GoalResponse response = new GoalResponse();
        response.setId(goal.getId());
        response.setName(goal.getName());
        response.setDescription(goal.getDescription());
        response.setCreateDate(goal.getCreateDate());
        response.setExpirationDate(goal.getExpirationDate());
        response.setProgresses(
                goal.getProgresses().stream().map(ProgressResponse::of).collect(Collectors.toList())
        );
        return response;
    }

    public static List<GoalResponse> of(List<Goal> goals) {
        return goals.stream().map(GoalResponse::of).collect(Collectors.toList());
    }
}
