package com.jeksvp.goalkeeper.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
public class Progress {

    private String name;
    private BigDecimal currentValue = new BigDecimal(0);
    private BigDecimal maxValue;

    public Progress(BigDecimal maxValue){
        this.maxValue = maxValue;
    }

}
