package com.jeksvp.bpd.web.dto.request.therapist;


import com.jeksvp.bpd.domain.entity.access.client.ClientAccess;
import com.jeksvp.bpd.domain.entity.access.therapist.TherapistAccess;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TherapistAccessFilter {

    private String status;

    public boolean passed(TherapistAccess therapistAccess) {
        if (StringUtils.isNotBlank(status)) {
            return status.equals(therapistAccess.getStatus().name());
        }
        return true;
    }
}
