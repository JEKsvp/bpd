package com.jeksvp.goalkeeper.web.dto.mapper;

import com.jeksvp.goalkeeper.domain.entity.Progress;
import com.jeksvp.goalkeeper.web.dto.request.ProgressRequest;

public class ProgressRequestMapper implements Mapper<ProgressRequest, Progress> {

    @Override
    public Progress map(ProgressRequest request) {
        return new Progress(request.getName(), request.getCurrentValue(), request.getMaxValue());
    }
}
