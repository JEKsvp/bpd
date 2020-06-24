package com.jeksvp.bpd.domain.entity.diary;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class OppositeThoughts {

    private String description;

    public static OppositeThoughts create(OppositeThoughts oppositeThoughts) {
        return OppositeThoughts.builder()
                .description(oppositeThoughts.getDescription())
                .build();
    }

    public OppositeThoughts update(OppositeThoughts oppositeThoughts) {
        this.description = oppositeThoughts.getDescription();
        return this;
    }
}
