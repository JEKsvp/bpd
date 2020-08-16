package com.jeksvp.bpd.web.dto.request;

import com.jeksvp.bpd.domain.entity.Role;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
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

    @AssertTrue(message = "Required fields for therapist is empty.")
    public boolean isValidTherapistFields() {
        if (Role.THERAPIST.equals(this.role)) {
            return isValidAsTherapist();
        }
        return true;
    }

    private boolean isValidAsTherapist() {
        return StringUtils.isNotBlank(this.firstName)
                && StringUtils.isNotBlank(this.lastName);
    }
}
