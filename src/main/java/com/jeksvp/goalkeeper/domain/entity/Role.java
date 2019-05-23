package com.jeksvp.goalkeeper.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name="T_ROLE")
public class Role {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String roleName;

    @Column(name="description")
    private String description;
}

