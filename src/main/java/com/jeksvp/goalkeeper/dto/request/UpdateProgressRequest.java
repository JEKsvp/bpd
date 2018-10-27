package com.jeksvp.goalkeeper.dto.request;

import lombok.Data;

@Data
public class UpdateProgressRequest {
    private Float currentValue;
    private Float maxValue;
}
