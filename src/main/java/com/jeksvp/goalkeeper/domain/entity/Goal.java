package com.jeksvp.goalkeeper.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
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

    public Goal(String name, String description, String username, LocalDateTime expirationDate) {
        this.name = name;
        this.description = description;
        this.username = username;
        this.createDate = LocalDateTime.now();
        this.expirationDate = expirationDate;
    }

    public void update(String name, String description, LocalDateTime expirationDate) {
        this.name = name;
        this.description = description;
        this.expirationDate = expirationDate;
    }

    public void addProgress(Progress progress) {
        this.progresses.add(progress);
    }
}
