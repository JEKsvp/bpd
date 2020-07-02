package com.jeksvp.bpd.web.dto.common;

import com.jeksvp.bpd.domain.entity.diary.MyThoughts;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyThoughtsDto {

    @NotBlank
    private String description;

    public static MyThoughtsDto create(MyThoughts myThoughts) {
        return MyThoughtsDto.builder()
                .description(myThoughts.getDescription())
                .build();
    }
}
