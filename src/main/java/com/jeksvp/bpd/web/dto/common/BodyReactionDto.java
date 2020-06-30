package com.jeksvp.bpd.web.dto.common;

import com.jeksvp.bpd.domain.entity.diary.BodyReaction;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BodyReactionDto {
    String description;

    public static BodyReactionDto create(BodyReaction bodyReaction) {
        return BodyReactionDto.builder()
                .description(bodyReaction.getDescription())
                .build();
    }
}
