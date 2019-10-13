package com.jeksvp.goalkeeper.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Progress {

    @Id
    private Long id;
    private Float currentValue = 0f;
    private Float maxValue;

    public Progress(Float maxValue){
        this.maxValue = maxValue;
    }

}
