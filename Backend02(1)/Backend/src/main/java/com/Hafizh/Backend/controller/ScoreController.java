package com.Hafizh.Backend.controller;

import com.Hafizh.Backend.model.Score;
import com.Hafizh.Backend.service.ScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {

    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping
    public ResponseEntity<List<Score>> getAllScores() {
        return ResponseEntity.ok(scoreService.getAllScores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getScoreById(@PathVariable UUID id) {
        return scoreService.getScoreById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Score> createScore(@RequestBody Score score) {
        return ResponseEntity.ok(scoreService.createScore(score));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScore(@PathVariable UUID id) {
        scoreService.deleteScore(id);
        return ResponseEntity.noContent().build();
    }
}

