package com.Hafizh.Backend.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class Player {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID id;
    private String username;
    private int highScore;
    private int totalCoins;
    private int totalDistance;

    public Player() {}

    public Player(String username, int highScore, int totalCoins, int totalDistance) {
        this.username = username;
        this.highScore = highScore;
        this.totalCoins = totalCoins;
        this.totalDistance = totalDistance;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public int getHighScore() { return highScore; }
    public void setHighScore(int highScore) { this.highScore = highScore; }

    public int getTotalCoins() { return totalCoins; }
    public void setTotalCoins(int totalCoins) { this.totalCoins = totalCoins; }

    public int getTotalDistance() { return totalDistance; }
    public void setTotalDistance(int totalDistance) { this.totalDistance = totalDistance; }
}
