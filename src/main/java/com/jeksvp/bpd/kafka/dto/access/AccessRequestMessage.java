package com.jeksvp.bpd.kafka.dto.access;

import com.jeksvp.bpd.utils.SecurityUtils;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

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

    public static AccessRequestMessage create(String toUsername) {
        return AccessRequestMessage.builder()
                .id(UUID.randomUUID().toString())
                .fromUsername(SecurityUtils.getCurrentUserName())
                .toUsername(toUsername)
                .createDate(LocalDateTime.now())
                .build();
    }
}
