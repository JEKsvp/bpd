package com.jeksvp.goalkeeper.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
@Document
public class Goal {

    @Id
    private String id;
    private String username;
    private List<Progress> progresses = new ArrayList<>();
    private String name;
    private String description;
    private LocalDateTime createDate;
    private LocalDateTime expirationDate;

    public Goal() {
    }

    public Goal(String name, String description, String username, LocalDateTime expirationDate) {
        this.name = name;
        this.description = description;
        this.username = username;
        this.createDate = LocalDateTime.now();
        this.expirationDate = expirationDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setProgresses(List<Progress> progresses) {
        this.progresses = progresses;
    }
}
