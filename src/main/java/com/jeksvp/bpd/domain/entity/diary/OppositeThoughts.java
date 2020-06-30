package com.jeksvp.bpd.domain.entity.diary;

import lombok.*;

@Builder(access = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class OppositeThoughts {

    private String description;

    public static OppositeThoughts create(String description) {
        return OppositeThoughts.builder()
                .description(description)
                .build();
    }

    public void update(String description) {
        this.description = description;
    }
}
