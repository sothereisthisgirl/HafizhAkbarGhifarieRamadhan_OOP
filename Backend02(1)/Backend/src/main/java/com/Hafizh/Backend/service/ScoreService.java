package com.Hafizh.Backend.service;

import com.Hafizh.Backend.model.Score;
import com.Hafizh.Backend.repository.ScoreRepository;
import com.Hafizh.Backend.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;

@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final PlayerRepository playerRepository;

    public ScoreService(ScoreRepository scoreRepository, PlayerRepository playerRepository) {
        this.scoreRepository = scoreRepository;
        this.playerRepository = playerRepository;
    }

    public List<Score> getAllScores() {
        return scoreRepository.findAll();
    }

    public Optional<Score> getScoreById(UUID id) {
        return scoreRepository.findById(id);
    }

    public List<Score> getScoresByPlayerId(UUID playerId) {
        return scoreRepository.findByPlayerId(playerId);
    }

    @Transactional
    public Score createScore(Score score) {
        return scoreRepository.save(score);
    }

    @Transactional
    public void deleteScore(UUID id) {
        scoreRepository.deleteById(id);
    }
}


