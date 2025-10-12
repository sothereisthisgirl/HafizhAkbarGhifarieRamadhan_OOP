package com.Hafizh.Backend.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class Score {

    @Id
    @GeneratedValue
    private UUID id;

    private int value;
    private int coins;
    private int distance;

    @ManyToOne
    private Player player;

    public Score() {}

    public Score(int value, int coins, int distance, Player player) {
        this.value = value;
        this.coins = coins;
        this.distance = distance;
        this.player = player;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }

    public int getCoins() { return coins; }
    public void setCoins(int coins) { this.coins = coins; }

    public int getDistance() { return distance; }
    public void setDistance(int distance) { this.distance = distance; }

    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }
}
