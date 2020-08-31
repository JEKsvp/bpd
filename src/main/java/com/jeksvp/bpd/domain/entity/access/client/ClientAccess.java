package com.jeksvp.bpd.domain.entity.access.client;

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
public class ClientAccess {

    private String username;

    private AccessStatus status;

    private LocalDateTime updateDate;

    public static ClientAccess create(String username, AccessStatus accessStatus, ClockSource clockSource) {
        return ClientAccess.builder()
                .username(username)
                .status(accessStatus)
                .updateDate(LocalDateTime.now(clockSource.getClock()))
                .build();
    }

    public void update(AccessStatus status, ClockSource clockSource) {
        this.status = status;
        this.updateDate = LocalDateTime.now(clockSource.getClock());
    }
}
