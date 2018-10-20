package com.jeksvp.goalkeeper.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = "goal")
@ToString(exclude = "goal")
@Entity(name = "t_progress")
public class Progress {

    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_goal")
    private Goal goal;

    @Column(name = "current_value")
    private Float currentValue;

    @Column(name = "max_value")
    private Float maxValue;
}
