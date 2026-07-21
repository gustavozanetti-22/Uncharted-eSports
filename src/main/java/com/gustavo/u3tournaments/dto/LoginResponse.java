package com.gustavo.u3tournaments.dto;

import com.gustavo.u3tournaments.enums.Role;

public class LoginResponse {

    private String message;
    private Long id;
    private String username;
    private String email;
    private String psnId;
    private Role role;

    public LoginResponse() {
    }

    public LoginResponse(
            String message,
            Long id,
            String username,
            String email,
            String psnId,
            Role role
    ) {
        this.message = message;
        this.id = id;
        this.username = username;
        this.email = email;
        this.psnId = psnId;
        this.role = role;
    }

    public String getMessage() {
        return message;
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
}