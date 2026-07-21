package com.gustavo.u3tournaments.service;

import com.gustavo.u3tournaments.dto.PlayerCreateRequest;
import com.gustavo.u3tournaments.dto.PlayerResponse;
import com.gustavo.u3tournaments.exception.DuplicatePlayerException;
import com.gustavo.u3tournaments.model.Player;
import com.gustavo.u3tournaments.repository.PlayerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;

    public PlayerService(
            PlayerRepository playerRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public PlayerResponse createPlayer(
            PlayerCreateRequest request
    ) {
        String username = request.getUsername().trim();

        String email = request
                .getEmail()
                .trim()
                .toLowerCase();

        String psnId = request.getPsnId().trim();

        if (playerRepository.existsByUsername(username)) {
            throw new DuplicatePlayerException(
                    "Username is already registered"
            );
        }

        if (playerRepository.existsByEmail(email)) {
            throw new DuplicatePlayerException(
                    "Email is already registered"
            );
        }

        if (playerRepository.existsByPsnId(psnId)) {
            throw new DuplicatePlayerException(
                    "PSN ID is already registered"
            );
        }

        Player player = new Player();

        player.setUsername(username);
        player.setEmail(email);

        player.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        player.setPsnId(psnId);
        player.setRole("PLAYER");

        Player savedPlayer = playerRepository.save(player);

        return convertToResponse(savedPlayer);
    }

    public List<PlayerResponse> getAllPlayers() {
        return playerRepository
                .findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    private PlayerResponse convertToResponse(Player player) {
        return new PlayerResponse(
                player.getId(),
                player.getUsername(),
                player.getEmail(),
                player.getPsnId(),
                player.getRole(),
                player.getCreatedAt()
        );
    }
}