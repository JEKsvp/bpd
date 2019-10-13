package com.jeksvp.goalkeeper.web.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jeksvp.goalkeeper.utils.json.LocalDateTimeDeserializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateGoalRequest {
    private String name;
    private String description;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime expirationDate;
}