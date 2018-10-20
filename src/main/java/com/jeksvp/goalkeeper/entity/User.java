package com.jeksvp.goalkeeper.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "T_USER")
@Data
@EqualsAndHashCode(exclude = "roles")
@ToString(exclude = "roles")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "T_USER_ROLE", joinColumns
            = @JoinColumn(name = "id_user",
            referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_role",
                    referencedColumnName = "id"))
    private List<Role> roles;
}

