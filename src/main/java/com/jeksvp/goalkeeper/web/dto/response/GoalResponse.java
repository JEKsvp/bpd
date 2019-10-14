package com.jeksvp.goalkeeper.web.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeksvp.goalkeeper.domain.entity.Goal;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
@ToString
@Builder
public class GoalResponse {
    private String id;
    private String name;
    private String description;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createDate;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime expirationDate;

    private List<ProgressResponse> progresses;

    public static GoalResponse of(Goal goal) {
        return GoalResponse.builder()
                .id(goal.getId())
                .name(goal.getName())
                .description(goal.getDescription())
                .createDate(goal.getCreateDate())
                .expirationDate(goal.getExpirationDate())
                .progresses(goal.getProgresses().stream()
                        .map(ProgressResponse::of)
                        .collect(Collectors.toList()))
                .build();
    }

    public static List<GoalResponse> of(List<Goal> goals) {
        return goals.stream().map(GoalResponse::of).collect(Collectors.toList());
    }
}
