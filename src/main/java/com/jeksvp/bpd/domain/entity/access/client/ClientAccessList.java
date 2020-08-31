package com.jeksvp.bpd.domain.entity.access.client;

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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class ClientAccessList {

    @Id
    private String username;

    private List<ClientAccess> accesses = new ArrayList<>();

    public static ClientAccessList create(String username) {
        return ClientAccessList.builder()
                .username(username)
                .accesses(new ArrayList<>())
                .build();
    }

    public void removeDeclinedAccesses() {
        this.accesses = this.accesses.stream()
                .filter(clientAccess -> !AccessStatus.DECLINE.equals(clientAccess.getStatus()))
                .collect(Collectors.toList());
    }

    public Optional<ClientAccess> findAccess(String username) {
        return accesses.stream()
                .filter(a -> a.getUsername().equals(username))
                .findFirst();
    }

    public void addAccess(ClientAccess clientAccess) {
        if (hasAccessStatusFor(clientAccess.getUsername())) {
            throw new DuplicateAccessException(MessageFormat.format("Access for user {0} already exists", clientAccess.getUsername()));
        } else {
            accesses.add(clientAccess);
        }
    }

    public boolean hasAccessStatusFor(String username) {
        return accesses.stream()
                .anyMatch(a -> a.getUsername().equals(username));
    }
}
