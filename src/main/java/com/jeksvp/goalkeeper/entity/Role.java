package com.jeksvp.goalkeeper.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name="T_ROLE")
public class Role {

    @Id
    private Long id;

    @Column(name="name")
    private String roleName;

    @Column(name="description")
    private String description;
}

