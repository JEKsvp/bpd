package com.jeksvp.bpd.web.dto.common;

import com.jeksvp.bpd.domain.entity.diary.EmotionalEvaluation;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmotionalEvaluationDto {

    @NotBlank
    private String description;

    public static EmotionalEvaluationDto create(EmotionalEvaluation emotionalEvaluation) {
        return EmotionalEvaluationDto.builder()
                .description(emotionalEvaluation.getDescription())
                .build();
    }
}
