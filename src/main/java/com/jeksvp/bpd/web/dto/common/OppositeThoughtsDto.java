package com.jeksvp.bpd.web.dto.common;

import com.jeksvp.bpd.domain.entity.diary.OppositeThoughts;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OppositeThoughtsDto {

    @NotBlank
    private String description;

    public static OppositeThoughtsDto create(OppositeThoughts oppositeThoughts) {
        return OppositeThoughtsDto.builder()
                .description(oppositeThoughts.getDescription())
                .build();
    }
}
