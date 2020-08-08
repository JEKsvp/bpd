package com.jeksvp.bpd.domain.entity.diary;

import lombok.*;

@Builder(access = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Event {

    private String description;

    public static Event create(String description) {
        return Event.builder()
                .description(description)
                .build();
    }

    public void update(String description) {
        this.description = description;
    }
}
