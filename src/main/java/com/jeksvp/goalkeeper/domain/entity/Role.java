package com.jeksvp.goalkeeper.domain.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
@Entity(name = "T_ROLE")
public class Role {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String roleName;

    @Column(name = "description")
    private String description;

    public Role(String roleName, String description) {
        this.roleName = roleName;
        this.description = description;
    }
}

