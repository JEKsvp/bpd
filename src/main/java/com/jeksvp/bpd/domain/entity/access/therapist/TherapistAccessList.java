package com.jeksvp.bpd.domain.entity.access.therapist;

import com.jeksvp.bpd.domain.entity.access.AccessStatus;
import com.jeksvp.bpd.domain.entity.access.DuplicateAccessException;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Document
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class TherapistAccessList {

    @Id
    private String username;

    private List<TherapistAccess> accesses;

    public static TherapistAccessList create(String username) {
        return TherapistAccessList.builder()
                .username(username)
                .accesses(new ArrayList<>())
                .build();
    }

    public void addAccess(TherapistAccess therapistAccess) {
        if (hasAccessStatusFor(therapistAccess.getUsername())) {
            throw new DuplicateAccessException(MessageFormat.format("Access for user {0} already exists", therapistAccess.getUsername()));
        } else {
            accesses.add(therapistAccess);
        }
    }

    public void removeDeclinedAccesses() {
        this.accesses = this.accesses.stream()
                .filter(clientAccess -> !AccessStatus.DECLINE.equals(clientAccess.getStatus()))
                .collect(Collectors.toList());
    }

    public Optional<TherapistAccess> findAccess(String username) {
        return accesses.stream()
                .filter(a -> a.getUsername().equals(username))
                .findFirst();
    }

    public boolean hasAccessStatusFor(String username) {
        return accesses.stream()
                .anyMatch(a -> a.getUsername().equals(username));
    }
}
