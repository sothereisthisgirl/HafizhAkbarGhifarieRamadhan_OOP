package Model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Score implements ShowDetail {
    private UUID playerId;
    private Player player;
    private int value;
    private int coinsCollected
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
}