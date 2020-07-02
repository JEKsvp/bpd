package com.jeksvp.bpd.web.dto.common;

import com.jeksvp.bpd.domain.entity.diary.BodyReaction;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BodyReactionDto {

    @NotBlank
    private String description;

    public static BodyReactionDto create(BodyReaction bodyReaction) {
        return BodyReactionDto.builder()
                .description(bodyReaction.getDescription())
                .build();
    }
}
