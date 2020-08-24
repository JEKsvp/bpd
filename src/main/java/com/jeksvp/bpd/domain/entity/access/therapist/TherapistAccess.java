package com.jeksvp.bpd.domain.entity.access.therapist;

import com.jeksvp.bpd.domain.entity.access.AccessStatus;
import com.jeksvp.bpd.utils.ClockSource;
import lombok.*;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class TherapistAccess {

    private String username;

    private AccessStatus status;

    private LocalDateTime updateDate;

    public static TherapistAccess create(String username, AccessStatus accessStatus, ClockSource clockSource) {
        return TherapistAccess.builder()
                .username(username)
                .status(accessStatus)
                .updateDate(LocalDateTime.now(clockSource.getClock()))
                .build();
    }
}
