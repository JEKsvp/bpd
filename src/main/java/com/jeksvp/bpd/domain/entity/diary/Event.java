package com.jeksvp.bpd.domain.entity.diary;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Event {

    private String description;

    public static Event create(Event event) {
        return Event.builder()
                .description(event.getDescription())
                .build();
    }

    public Event update(Event event) {
        this.description = event.getDescription();
        return this;
    }
}
