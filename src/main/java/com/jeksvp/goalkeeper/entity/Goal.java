package com.jeksvp.goalkeeper.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeksvp.goalkeeper.dto.request.CreateGoalRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
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
    private List<Progress> progresses;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    public static Goal of(CreateGoalRequest request) {
        Goal goal = new Goal();
        goal.setName(request.getName());
        goal.setDescription(request.getDescription());
        goal.setExpirationDate(request.getExpirationDate());
        goal.setProgresses(Progress.of(request.getProgresses()));
        goal.getProgresses().forEach(progress -> progress.setGoal(goal));
        return goal;
    }
}
