package com.jeksvp.bpd.web.dto.request.diary;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateDiaryRequest {

    @NotBlank
    private String name;
}
