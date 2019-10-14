package com.jeksvp.goalkeeper.web.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class ProgressRequest {
    private String name;
    private BigDecimal maxValue;
    private BigDecimal currentValue;
}
