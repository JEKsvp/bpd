package com.jeksvp.goalkeeper.web.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jeksvp.goalkeeper.utils.json.LocalDateTimeDeserializer;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class GoalRequest {

    @NotBlank
    private String name;
    private String description;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @NotNull
    private LocalDateTime expirationDate;

    @NotEmpty
    private List<ProgressRequest> progresses = new ArrayList<>();
}
