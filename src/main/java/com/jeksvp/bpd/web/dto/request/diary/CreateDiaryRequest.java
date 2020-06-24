package com.jeksvp.bpd.web.dto.request.diary;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateDiaryRequest {

    @NotBlank
    private String name;
}
