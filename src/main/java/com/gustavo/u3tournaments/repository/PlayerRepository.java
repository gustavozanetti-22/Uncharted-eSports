package com.gustavo.u3tournaments.repository;

import com.gustavo.u3tournaments.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByEmail(String email);

    Optional<Player> findByEmailIgnoreCase(String email);

    Optional<Player> findByUsername(String username);

    Optional<Player> findByPsnId(String psnId);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByPsnId(String psnId);
}