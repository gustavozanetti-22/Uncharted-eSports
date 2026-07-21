package com.gustavo.u3tournaments.dto;

import com.gustavo.u3tournaments.enums.Role;

import java.time.LocalDateTime;

public class PlayerResponse {

    private Long id;
    private String username;
    private String email;
    private String psnId;
    private Role role;
    private LocalDateTime createdAt;

    public PlayerResponse() {
    }

    public PlayerResponse(
            Long id,
            String username,
            String email,
            String psnId,
            Role role,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.psnId = psnId;
        this.role = role;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPsnId() {
        return psnId;
    }

    public Role getRole() {
        return role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}