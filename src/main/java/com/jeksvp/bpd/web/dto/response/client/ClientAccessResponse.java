package com.jeksvp.bpd.web.dto.response.client;

import com.jeksvp.bpd.domain.entity.access.client.ClientAccess;
import com.jeksvp.bpd.domain.entity.access.therapist.TherapistAccess;
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
    private String status;

    public static ClientAccessResponse create(ClientAccess clientAccess) {
        return ClientAccessResponse.builder()
                .username(clientAccess.getUsername())
                .status(clientAccess.getStatus().name())
                .build();
    }
}
