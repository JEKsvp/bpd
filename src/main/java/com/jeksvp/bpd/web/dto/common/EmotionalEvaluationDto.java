package com.jeksvp.bpd.web.dto.common;

import com.jeksvp.bpd.domain.entity.diary.EmotionalEvaluation;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmotionalEvaluationDto {
    String description;

    public static EmotionalEvaluationDto create(EmotionalEvaluation emotionalEvaluation) {
        return EmotionalEvaluationDto.builder()
                .description(emotionalEvaluation.getDescription())
                .build();
    }
}
