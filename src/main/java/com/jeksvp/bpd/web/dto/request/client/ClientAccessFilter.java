package com.jeksvp.bpd.web.dto.request.client;


import com.jeksvp.bpd.domain.entity.access.client.ClientAccess;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientAccessFilter {

    private String status;

    public boolean passed(ClientAccess clientAccess) {
        if (StringUtils.isNotBlank(status)) {
            return status.equals(clientAccess.getStatus().name());
        }
        return true;
    }
}
