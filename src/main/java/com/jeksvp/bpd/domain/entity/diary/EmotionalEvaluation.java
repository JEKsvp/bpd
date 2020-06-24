package com.jeksvp.bpd.domain.entity.diary;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class EmotionalEvaluation {

    private String description;

    public static EmotionalEvaluation create(EmotionalEvaluation emotionalEvaluation) {
        return EmotionalEvaluation.builder()
                .description(emotionalEvaluation.getDescription())
                .build();
    }

    public EmotionalEvaluation update(EmotionalEvaluation emotionalEvaluation) {
        this.description = emotionalEvaluation.description;
        return this;
    }
}
