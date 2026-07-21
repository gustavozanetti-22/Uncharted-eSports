package com.gustavo.u3tournaments.service;

import com.gustavo.u3tournaments.dto.LoginRequest;
import com.gustavo.u3tournaments.dto.LoginResponse;
import com.gustavo.u3tournaments.exception.InvalidCredentialsException;
import com.gustavo.u3tournaments.model.Player;
import com.gustavo.u3tournaments.repository.PlayerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            PlayerRepository playerRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {

        String email = request
                .getEmail()
                .trim()
                .toLowerCase();

        Player player = playerRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() ->
                        new InvalidCredentialsException(
                                "Invalid email or password"
                        )
                );

        boolean passwordMatches =
                passwordEncoder.matches(
                        request.getPassword(),
                        player.getPassword()
                );

        if (!passwordMatches) {
            throw new InvalidCredentialsException(
                    "Invalid email or password"
            );
        }

        String accessToken =
                jwtService.generateToken(player);

        return new LoginResponse(
                "Login successful",
                accessToken,
                "Bearer",
                jwtService.getExpirationSeconds(),
                player.getId(),
                player.getUsername(),
                player.getEmail(),
                player.getPsnId(),
                player.getRole()
        );
    }
}