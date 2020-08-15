package com.jeksvp.bpd.domain.entity.access.client;

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
public class ClientAccessList {

    @Id
    private String username;

    private List<ClientAccess> accesses;

    public static ClientAccessList create(String username) {
        return ClientAccessList.builder()
                .username(username)
                .accesses(new ArrayList<>())
                .build();
    }
}
