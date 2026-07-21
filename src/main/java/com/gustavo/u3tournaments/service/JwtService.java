package com.gustavo.u3tournaments.service;

import com.gustavo.u3tournaments.model.Player;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final String issuer;
    private final long expirationSeconds;

    public JwtService(
            JwtEncoder jwtEncoder,
            @Value("${jwt.issuer}") String issuer,
            @Value("${jwt.expiration-seconds}")
            long expirationSeconds
    ) {
        this.jwtEncoder = jwtEncoder;
        this.issuer = issuer;
        this.expirationSeconds = expirationSeconds;
    }

    public String generateToken(Player player) {

        Instant issuedAt = Instant.now();

        Instant expiresAt = issuedAt.plusSeconds(
                expirationSeconds
        );

        String authority =
                "ROLE_" + player.getRole().name();

        JwtClaimsSet claims = JwtClaimsSet
                .builder()
                .id(UUID.randomUUID().toString())
                .issuer(issuer)
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .subject(player.getEmail())
                .claim("playerId", player.getId())
                .claim("username", player.getUsername())
                .claim("psnId", player.getPsnId())
                .claim("role", player.getRole().name())
                .claim("roles", List.of(authority))
                .build();

        JwsHeader header = JwsHeader
                .with(MacAlgorithm.HS256)
                .type("JWT")
                .build();

        return jwtEncoder
                .encode(
                        JwtEncoderParameters.from(
                                header,
                                claims
                        )
                )
                .getTokenValue();
    }

    public long getExpirationSeconds() {
        return expirationSeconds;
    }
}