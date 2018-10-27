package com.jeksvp.goalkeeper.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jeksvp.goalkeeper.utils.json.LocalDateTimeDeserializer;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class UpdateGoalRequest {
    private String name;
    private String description;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime expirationDate;
}
