package com.jeksvp.bpd.web.dto.response;

import com.jeksvp.bpd.domain.entity.access.therapist.TherapistAccess;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TherapistAccessResponse {
    private String username;

    public static TherapistAccessResponse create(TherapistAccess therapistAccess) {
        return TherapistAccessResponse.builder()
                .username(therapistAccess.getUsername())
                .build();
    }
}
