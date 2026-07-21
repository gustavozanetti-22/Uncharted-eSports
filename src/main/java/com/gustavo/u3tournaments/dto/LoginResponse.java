package com.gustavo.u3tournaments.dto;

public class LoginResponse {

    private String message;
    private Long id;
    private String username;
    private String email;
    private String psnId;
    private String role;

    public LoginResponse() {
    }

    public LoginResponse(
            String message,
            Long id,
            String username,
            String email,
            String psnId,
            String role
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

    public String getRole() {
        return role;
    }
}