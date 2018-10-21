package com.jeksvp.goalkeeper.dto.response;

import com.jeksvp.goalkeeper.entity.Progress;
import lombok.Data;

@Data
public class ProgressesResponse {
    private Long id;
    private Float currentValue;
    private Float maxValue;

    public static ProgressesResponse of(Progress progress){
        ProgressesResponse response = new ProgressesResponse();
        response.setId(progress.getId());
        response.setCurrentValue(progress.getCurrentValue());
        response.setMaxValue(progress.getMaxValue());
        return response;
    }
}
