package com.jeksvp.bpd.domain.entity.diary;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class BodyReaction {

    private String description;

    public static BodyReaction create(BodyReaction bodyReaction) {
        return BodyReaction.builder()
                .description(bodyReaction.getDescription())
                .build();
    }

    public BodyReaction update(BodyReaction bodyReaction) {
        this.description = description;
        return this;
    }
}
