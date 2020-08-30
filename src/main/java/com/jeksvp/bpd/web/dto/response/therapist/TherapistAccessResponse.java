package com.jeksvp.bpd.web.dto.response.therapist;

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
    private String status;

    public static TherapistAccessResponse create(TherapistAccess therapistAccess) {
        return TherapistAccessResponse.builder()
                .username(therapistAccess.getUsername())
                .status(therapistAccess.getStatus().name())
                .build();
    }
}
