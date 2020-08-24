package com.jeksvp.bpd.domain.entity.access.therapist;

import com.jeksvp.bpd.domain.entity.User;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class TherapistAccessList {

    @Id
    private String username;

    private List<TherapistAccess> accesses = new ArrayList<>();

    public static TherapistAccessList create(String username) {
        return TherapistAccessList.builder()
                .username(username)
                .accesses(new ArrayList<>())
                .build();
    }

    public void addAccess(TherapistAccess access) {
        accesses.add(access);
    }
}
