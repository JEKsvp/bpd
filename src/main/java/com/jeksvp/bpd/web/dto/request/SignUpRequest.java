package com.jeksvp.bpd.web.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @Size(min = 1, max = 20)
    private String username;

    @Size(min = 6, max = 30)
    private String password;

    @NotBlank
    private String email;
}
