package com.jeksvp.bpd.web.dto.response.therapist;


import com.jeksvp.bpd.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TherapistResponse {
    private String username;
    private String firstName;
    private String lastName;
    private String aboutMe;

    public static TherapistResponse create(User user) {
        return TherapistResponse.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .aboutMe(user.getAboutMe())
                .build();
    }
}
