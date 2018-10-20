package com.jeksvp.goalkeeper.entity;

import lombok.Data;

import javax.persistence.*;


@Entity
@Table(name = "T_RANDOM_CITY")
@Data
public class RandomCity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

}
