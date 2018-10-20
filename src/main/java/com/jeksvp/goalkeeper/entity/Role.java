package com.jeksvp.goalkeeper.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="T_ROLE")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String roleName;

    @Column(name="description")
    private String description;
}
