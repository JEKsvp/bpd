package com.jeksvp.goalkeeper.web.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jeksvp.goalkeeper.utils.json.LocalDateTimeDeserializer;
import lombok.Data;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreateGoalRequest {

    @NotBlank
    private String name;
    private String description;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime expirationDate;

    @NotEmpty
    private List<CreateProgressRequest> progresses = new ArrayList<>();
}
