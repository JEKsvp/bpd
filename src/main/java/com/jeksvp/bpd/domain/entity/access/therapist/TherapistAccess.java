package com.jeksvp.bpd.domain.entity.access.therapist;

import com.jeksvp.bpd.domain.entity.access.AccessStatus;
import lombok.*;

@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class TherapistAccess {

    private String username;

    private AccessStatus status;
}
