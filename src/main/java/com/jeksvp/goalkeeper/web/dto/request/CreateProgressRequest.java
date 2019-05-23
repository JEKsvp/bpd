package com.jeksvp.goalkeeper.web.dto.request;

import lombok.Data;

@Data
public class CreateProgressRequest {
    private Float maxValue;
    private Long goalId;
}
