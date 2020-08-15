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
public class TherapistResponse {
    private String username;

    public static TherapistResponse create(TherapistAccess therapistAccess) {
        return TherapistResponse.builder()
                .username(therapistAccess.getUsername())
                .build();
    }
}
