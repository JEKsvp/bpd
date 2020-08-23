package com.jeksvp.bpd.kafka.dto.access;

import com.jeksvp.bpd.utils.ClockSource;
import com.jeksvp.bpd.utils.SecurityUtils;
import com.jeksvp.bpd.utils.UuidSource;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.Clock;
import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class AccessRequestMessage {

    @NotBlank
    private String id;

    @NotBlank
    private String fromUsername;

    @NotBlank
    private String toUsername;

    @NotBlank
    private LocalDateTime createDate;

    public static AccessRequestMessage create(String toUsername, ClockSource clockSource, UuidSource uuidSource) {
        return AccessRequestMessage.builder()
                .id(uuidSource.random().toString())
                .fromUsername(SecurityUtils.getCurrentUserName())
                .toUsername(toUsername)
                .createDate(LocalDateTime.now(clockSource.getClock()))
                .build();
    }
}
