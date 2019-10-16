package com.jeksvp.goalkeeper.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Progress {

    private String name;
    private BigDecimal currentValue = new BigDecimal(0);
    private BigDecimal maxValue;

    public Progress(String name, BigDecimal maxValue){
        this.name = name;
        this.maxValue = maxValue;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }

    public void setMaxValue(BigDecimal maxValue) {
        this.maxValue = maxValue;
    }
}
