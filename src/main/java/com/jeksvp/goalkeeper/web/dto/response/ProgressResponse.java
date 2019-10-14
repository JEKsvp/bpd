package com.jeksvp.goalkeeper.web.dto.response;

import com.jeksvp.goalkeeper.domain.entity.Progress;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
@ToString
@Builder
public class ProgressResponse {
    private String name;
    private BigDecimal currentValue;
    private BigDecimal maxValue;

    public static ProgressResponse of(Progress progress) {
        return ProgressResponse.builder()
                .name(progress.getName())
                .currentValue(progress.getCurrentValue())
                .maxValue(progress.getMaxValue())
                .build();
    }
}
