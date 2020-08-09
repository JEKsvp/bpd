package com.jeksvp.bpd.web.dto.request;

import com.jeksvp.bpd.domain.entity.Role;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @Size(min = 1, max = 30)
    @Pattern(regexp = "^[\\w]*$", message = "Username can contains only alphanumeric and underscore.")
    private String username;

    @Size(min = 6, max = 50)
    private String password;

    @NotBlank
    private String email;

    @NotNull
    private Role role;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    private String aboutMe;

    @AssertTrue(message = "Required fields for psychotherapist is empty.")
    public boolean isValidPsychotherapistFields() {
        if (Role.PSYCHOTHERAPIST.equals(this.role)) {
            return isValidAsPsychotherapist();
        }
        return true;
    }

    private boolean isValidAsPsychotherapist() {
        return StringUtils.isNotBlank(this.firstName)
                && StringUtils.isNotBlank(this.lastName);
    }
}
