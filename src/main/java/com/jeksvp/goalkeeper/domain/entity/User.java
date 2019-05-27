package com.jeksvp.goalkeeper.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = "roles")
@ToString(exclude = "roles")
@Entity(name = "T_USER")
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
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Goal> goals = new ArrayList<>();

    public User(String username, String password, String email, Role role) {
        this.username = username;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
        this.email = email;
        this.roles.add(role);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void changeEmail(String newEmail) {
        this.email = newEmail;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void addGoal(Goal goal) {
        this.goals.add(goal);
    }
}

