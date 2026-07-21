package com.gustavo.u3tournaments.model;

import com.gustavo.u3tournaments.enums.Role;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PLAYERS")
public class Player {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "player_sequence"
    )
    @SequenceGenerator(
            name = "player_sequence",
            sequenceName = "SEQ_PLAYERS",
            allocationSize = 1
    )
    private Long id;

    @Column(
            name = "USERNAME",
            nullable = false,
            unique = true,
            length = 50
    )
    private String username;

    @Column(
            name = "EMAIL",
            nullable = false,
            unique = true,
            length = 100
    )
    private String email;

    @Column(
            name = "PASSWORD",
            nullable = false,
            length = 255
    )
    private String password;

    @Column(
            name = "PSN_ID",
            nullable = false,
            unique = true,
            length = 50
    )
    private String psnId;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "USER_ROLE",
            nullable = false,
            length = 20
    )
    private Role role = Role.PLAYER;

    @Column(
            name = "CREATED_AT",
            nullable = false
    )
    private LocalDateTime createdAt;

    public Player() {
    }

    @PrePersist
    public void beforeSave() {

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }

        if (role == null) {
            role = Role.PLAYER;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPsnId() {
        return psnId;
    }

    public void setPsnId(String psnId) {
        this.psnId = psnId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}