package com.jeksvp.goalkeeper.domain.entity;

import com.jeksvp.goalkeeper.web.dto.request.CreateProgressRequest;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = "goal")
@ToString(exclude = "goal")
@Entity(name = "t_progress")
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_goal")
    private Goal goal;

    @Column(name = "current_value")
    private Float currentValue = 0f;

    @Column(name = "max_value")
    private Float maxValue;

    public Progress(Float maxValue, Goal goal){
        this.maxValue = maxValue;
        this.goal = goal;
    }

    public void update(Float currentValue, Float maxValue) {
        this.currentValue = currentValue;
        this.maxValue = maxValue;
    }
}
