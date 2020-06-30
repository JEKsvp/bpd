package com.jeksvp.bpd.web.dto.common;

import com.jeksvp.bpd.domain.entity.diary.OppositeThoughts;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OppositeThoughtsDto {
    String description;

    public static OppositeThoughtsDto create(OppositeThoughts oppositeThoughts) {
        return OppositeThoughtsDto.builder()
                .description(oppositeThoughts.getDescription())
                .build();
    }
}
