package com.jeksvp.bpd.web.dto.request.access;


import com.jeksvp.bpd.domain.entity.access.Access;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessFilter {

    private String status;

    public boolean passed(Access access) {
        if (StringUtils.isNotBlank(status)) {
            return status.equals(access.getStatus().name());
        }
        return true;
    }
}
