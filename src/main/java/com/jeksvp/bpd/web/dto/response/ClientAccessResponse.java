package com.jeksvp.bpd.web.dto.response;

import com.jeksvp.bpd.domain.entity.access.patient.ClientAccess;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientAccessResponse {

    private String username;

    public static ClientAccessResponse create(ClientAccess clientAccess) {
        return ClientAccessResponse.builder()
                .username(clientAccess.getUsername())
                .build();
    }
}
