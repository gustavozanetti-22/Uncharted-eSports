package com.gustavo.u3tournaments.controller;

import com.gustavo.u3tournaments.dto.PlayerCreateRequest;
import com.gustavo.u3tournaments.dto.PlayerResponse;
import com.gustavo.u3tournaments.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    public ResponseEntity<PlayerResponse> createPlayer(
            @Valid @RequestBody PlayerCreateRequest request
    ) {
        PlayerResponse createdPlayer =
                playerService.createPlayer(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdPlayer);
    }

    @GetMapping
    public ResponseEntity<List<PlayerResponse>> getAllPlayers() {
        return ResponseEntity.ok(
                playerService.getAllPlayers()
        );
    }
}