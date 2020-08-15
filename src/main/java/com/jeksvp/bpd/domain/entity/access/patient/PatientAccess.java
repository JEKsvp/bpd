package com.jeksvp.bpd.domain.entity.access.patient;

import com.jeksvp.bpd.domain.entity.access.AccessStatus;
import lombok.*;

@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class PatientAccess {

    private String username;

    private AccessStatus status;
}
