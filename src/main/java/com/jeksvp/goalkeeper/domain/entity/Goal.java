package com.jeksvp.goalkeeper.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeksvp.goalkeeper.web.dto.request.CreateGoalRequest;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = "user")
@ToString(exclude = "user")
@Entity(name = "t_goal")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "goal", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Progress> progresses = new ArrayList<>();

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    public Goal(String name, String description, User user, LocalDateTime expirationDate){
        this.name = name;
        this.description = description;
        this.user = user;
        this.createDate = LocalDateTime.now();
        this.expirationDate = expirationDate;
    }

    public void update(String name, String description, LocalDateTime expirationDate){
        this.name = name;
        this.description = description;
        this.expirationDate = expirationDate;
    }

    public void addProgress(Progress progress){
        this.progresses = progresses;
    }
}
