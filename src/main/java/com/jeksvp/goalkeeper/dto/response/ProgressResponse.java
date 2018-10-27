package com.jeksvp.goalkeeper.dto.response;

import com.jeksvp.goalkeeper.entity.Progress;
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
