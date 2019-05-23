package com.jeksvp.goalkeeper.web.dto.response;

import com.jeksvp.goalkeeper.domain.entity.Progress;
import lombok.Data;

@Data
public class ProgressResponse {
    private Long id;
    private Float currentValue;
    private Float maxValue;

    public static ProgressResponse of(Progress progress){
        ProgressResponse response = new ProgressResponse();
        response.setId(progress.getId());
        response.setCurrentValue(progress.getCurrentValue());
        response.setMaxValue(progress.getMaxValue());
        return response;
    }
}
