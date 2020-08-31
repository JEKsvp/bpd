package com.jeksvp.bpd.domain.entity.access;

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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class AccessList {

    @Id
    private String username;

    private List<Access> accesses = new ArrayList<>();

    public static AccessList create(String username) {
        return AccessList.builder()
                .username(username)
                .accesses(new ArrayList<>())
                .build();
    }

    public void removeDeclinedAccesses() {
        this.accesses = this.accesses.stream()
                .filter(clientAccess -> !AccessStatus.DECLINE.equals(clientAccess.getStatus()))
                .collect(Collectors.toList());
    }

    public Optional<Access> findAccess(String username) {
        return accesses.stream()
                .filter(a -> a.getUsername().equals(username))
                .findFirst();
    }

    public void addAccess(Access access) {
        if (hasAccessStatusFor(access.getUsername())) {
            throw new DuplicateAccessException(MessageFormat.format("Access for user {0} already exists", access.getUsername()));
        } else {
            accesses.add(access);
        }
    }

    public boolean hasAccessStatusFor(String username) {
        return accesses.stream()
                .anyMatch(a -> a.getUsername().equals(username));
    }

    public boolean hasAccessStatusFor(String username, AccessStatus accessStatus) {
        return accesses.stream()
                .anyMatch(access -> access.getUsername().equals(username) && accessStatus.equals(access.getStatus()));
    }
}
