package com.jeksvp.bpd.domain.entity.access.client;

import com.jeksvp.bpd.domain.entity.access.AccessStatus;
import lombok.*;

@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class ClientAccess {

    private String username;

    private AccessStatus status;
}
