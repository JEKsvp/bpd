package com.jeksvp.bpd.web.dto.common;

import com.jeksvp.bpd.domain.entity.diary.Event;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private String description;

    public static EventDto create(Event event) {
        return EventDto.builder()
                .description(event.getDescription())
                .build();
    }
}
