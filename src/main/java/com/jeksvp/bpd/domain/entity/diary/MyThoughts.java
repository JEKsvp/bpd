package com.jeksvp.bpd.domain.entity.diary;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class MyThoughts {

    private String description;

    public static MyThoughts create(MyThoughts myThoughts) {
        return MyThoughts.builder()
                .description(myThoughts.getDescription())
                .build();
    }

    public MyThoughts update(MyThoughts myThoughts) {
        this.description = myThoughts.description;
        return this;
    }
}
