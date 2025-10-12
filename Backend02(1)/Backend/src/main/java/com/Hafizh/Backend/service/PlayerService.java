package com.Hafizh.Backend.service;

import com.Hafizh.Backend.model.Player;
import com.Hafizh.Backend.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Optional<Player> getPlayerById(UUID id) {
        return playerRepository.findById(id);
    }

    public Optional<Player> getPlayerByUsername(String username) {
        return playerRepository.findByUsername(username);
    }

    public boolean isUsernameExists(String username) {
        return playerRepository.existsByUsername(username);
    }

    @Transactional
    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }

    @Transactional
    public Player updatePlayer(UUID id, Player updatedPlayer) {
        return playerRepository.findById(id)
                .map(player -> {
                    player.setUsername(updatedPlayer.getUsername());
                    player.setHighScore(updatedPlayer.getHighScore());
                    player.setTotalCoins(updatedPlayer.getTotalCoins());
                    player.setTotalDistance(updatedPlayer.getTotalDistance());
                    return playerRepository.save(player);
                })
                .orElseThrow(() -> new RuntimeException("Player not found"));
    }

    @Transactional
    public void deletePlayer(UUID id) {
        playerRepository.deleteById(id);
    }

    public List<Player> getLeaderboardByHighScore(int limit) {
        return playerRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Player::getHighScore).reversed())
                .limit(limit)
                .toList();
    }

    public List<Player> getLeaderboardByTotalCoins() {
        return playerRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Player::getTotalCoins).reversed())
                .toList();
    }

    public List<Player> getLeaderboardByTotalDistance() {
        return playerRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Player::getTotalDistance).reversed())
                .toList();
    }
}
