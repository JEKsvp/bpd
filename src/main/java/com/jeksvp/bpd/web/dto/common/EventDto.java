package com.jeksvp.bpd.web.dto.common;

import com.jeksvp.bpd.domain.entity.diary.Event;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {

    @NotBlank
    private String description;

    public static EventDto create(Event event) {
        return EventDto.builder()
                .description(event.getDescription())
                .build();
    }
}
