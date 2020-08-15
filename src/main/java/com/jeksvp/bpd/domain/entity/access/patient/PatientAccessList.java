package com.jeksvp.bpd.domain.entity.access.patient;

import com.jeksvp.bpd.domain.entity.User;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class PatientAccessList {

    @Id
    private String username;

    private List<PatientAccess> accesses;

    public static PatientAccessList create(User user) {
        return PatientAccessList.builder()
                .username(user.getUsername())
                .accesses(new ArrayList<>())
                .build();
    }
}
