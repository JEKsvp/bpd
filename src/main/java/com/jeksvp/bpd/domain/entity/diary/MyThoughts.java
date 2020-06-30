package com.jeksvp.bpd.domain.entity.diary;

import lombok.*;

@Builder(access = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class MyThoughts {

    private String description;

    public static MyThoughts create(String description) {
        return MyThoughts.builder()
                .description(description)
                .build();
    }

    public void update(String description) {
        this.description = description;
    }
}
