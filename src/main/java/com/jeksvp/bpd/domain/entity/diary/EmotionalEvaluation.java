package com.jeksvp.bpd.domain.entity.diary;

import lombok.*;

@Builder(access = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class EmotionalEvaluation {

    private String description;

    public static EmotionalEvaluation create(String description) {
        return EmotionalEvaluation.builder()
                .description(description)
                .build();
    }

    public void update(String description) {
        this.description = description;
    }
}
