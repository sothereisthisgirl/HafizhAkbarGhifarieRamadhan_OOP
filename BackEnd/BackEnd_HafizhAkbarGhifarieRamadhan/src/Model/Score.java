package Model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Score implements ShowDetail {
    private UUID scoreId;
    private UUID playerId;
    private Player player;
    private int value;
    private int coinsCollected;
    private int distance;
    private LocalDateTime createdAt;

    public Score(Player player, int value, int coinsCollected, int distance){
        this.scoreId = UUID.randomUUID();
        this.playerId = player.getPlayerId();
        this.player = player;
        this.value = value;
        this.coinsCollected = coinsCollected;
        this.distance = distance;
        this.createdAt = LocalDateTime.now();
    }
    public int getValue() {
        return value;
    }
    public int getCoinsCollected() {
        return coinsCollected;
    }
    public int getDistance() {
        return distance;
    }
    public void showDetail() {
        System.out.println("Score ID: " + scoreId);
        System.out.println("Player ID: " + playerId);
        System.out.println("Score Value: " + value);
        System.out.println("Coins Collected: " + coinsCollected);
        System.out.println("Distance: " + distance);
        System.out.println("Created At: " + createdAt);
        System.out.println();
    }
}