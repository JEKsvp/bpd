package com.jeksvp.bpd.web.dto.request.therapist;

import com.jeksvp.bpd.web.dto.request.PageableFilter;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TherapistPageableFilter extends PageableFilter {
    private String query;
}



