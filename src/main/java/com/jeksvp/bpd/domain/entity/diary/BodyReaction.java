package com.jeksvp.bpd.domain.entity.diary;

import lombok.*;

@Builder(access = AccessLevel.PRIVATE)

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class BodyReaction {

    private String description;

    public static BodyReaction create(String description) {
        return BodyReaction.builder()
                .description(description)
                .build();
    }

    public void update(String description) {
        this.description = description;
    }
}
