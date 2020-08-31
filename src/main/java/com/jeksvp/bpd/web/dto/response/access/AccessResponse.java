package com.jeksvp.bpd.web.dto.response.access;

import com.jeksvp.bpd.domain.entity.access.Access;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessResponse {

    private String username;
    private String status;

    public static AccessResponse create(Access access) {
        return AccessResponse.builder()
                .username(access.getUsername())
                .status(access.getStatus().name())
                .build();
    }
}
