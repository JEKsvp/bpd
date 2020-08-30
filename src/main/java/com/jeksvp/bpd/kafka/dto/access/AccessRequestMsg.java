package com.jeksvp.bpd.kafka.dto.access;

import com.jeksvp.bpd.utils.ClockSource;
import com.jeksvp.bpd.utils.SecurityUtils;
import com.jeksvp.bpd.utils.UuidSource;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class AccessRequestMsg {

    @NotBlank
    private String id;

    @NotBlank
    private String fromClientUsername;

    @NotBlank
    private String toTherapistUsername;

    @NotBlank
    private AccessStatusMsg status;

    @NotBlank
    private LocalDateTime createDate;

    public static AccessRequestMsg create(String toTherapistUsername,
                                          AccessStatusMsg accessStatus,
                                          ClockSource clockSource,
                                          UuidSource uuidSource) {
        return AccessRequestMsg.builder()
                .id(uuidSource.random().toString())
                .fromClientUsername(SecurityUtils.getCurrentUserName())
                .toTherapistUsername(toTherapistUsername)
                .status(accessStatus)
                .createDate(LocalDateTime.now(clockSource.getClock()))
                .build();
    }
}
