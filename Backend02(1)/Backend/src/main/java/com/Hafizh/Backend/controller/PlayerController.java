package com.Hafizh.Backend.controller;

import com.Hafizh.Backend.model.Player;
import com.Hafizh.Backend.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers() {
        return ResponseEntity.ok(playerService.getAllPlayers());
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<?> getPlayerById(@PathVariable UUID playerId) {
        Optional<Player> player = playerService.getPlayerById(playerId);
        if (player.isPresent()) {
            return ResponseEntity.ok(player.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player not found");
        }
    }


    @PostMapping
    public ResponseEntity<?> createPlayer(@RequestBody Player player) {
        return ResponseEntity.status(HttpStatus.CREATED).body(playerService.createPlayer(player));
    }
}