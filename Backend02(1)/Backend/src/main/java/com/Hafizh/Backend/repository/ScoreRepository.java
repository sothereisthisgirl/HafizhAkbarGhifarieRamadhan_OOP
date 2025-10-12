package com.Hafizh.Backend.repository;

import com.Hafizh.Backend.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ScoreRepository extends JpaRepository<Score, UUID> {
    List<Score> findByPlayerId(UUID playerId);
}

