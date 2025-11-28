package com.HafizhAkbarGhifarieRamadhan.frontend;

import com.HafizhAkbarGhifarieRamadhan.frontend.observers.ScoreManager;
import com.HafizhAkbarGhifarieRamadhan.frontend.service.BackendService;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class GameManager {
    private static GameManager instance;

    private ScoreManager scoreManager;
    private boolean gameActive;

    private BackendService backendService;
    private String currentPlayerId;
    private int coinsCollected;

    private GameManager() {
        scoreManager = new ScoreManager();
        gameActive = false;
        backendService = new BackendService();
        currentPlayerId = null;
        coinsCollected = 0;
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void registerPlayer(String username) {
        backendService.createPlayer(username, new BackendService.RequestCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JsonValue json = new JsonReader().parse(response);
                    currentPlayerId = json.getString("playerId");
                    Gdx.app.log("BackendService", "Player ID: " + currentPlayerId);
                } catch (Exception e) {
                    Gdx.app.error("BackendService", "Parse error: " + e.getMessage());
                }
            }

            @Override
            public void onError(String error) {
                Gdx.app.error("BackendService", "Register error: " + error);
            }
        });
    }

    public void startGame() {
        scoreManager.setScore(0);
        coinsCollected = 0;
        gameActive = true;
        Gdx.app.log("GameManager", "Game Started");
    }

    public void endGame() {
        if (currentPlayerId == null) {
            Gdx.app.error("BackendService", "Cannot submit score, playerId is null");
            return;
        }

        int distance = scoreManager.getScore();
        int finalScore = distance + coinsCollected * 10;

        backendService.submitScore(
            currentPlayerId,
            finalScore,
            coinsCollected,
            distance,
            new BackendService.RequestCallback() {
                @Override
                public void onSuccess(String response) {
                    Gdx.app.log("BackendService", "Score submit success: " + response);
                }

                @Override
                public void onError(String error) {
                    Gdx.app.error("BackendService", "Score submit error: " + error);
                }
            }
        );

        gameActive = false;
    }

    public void addCoin() {
        coinsCollected++;
        Gdx.app.log("GameManager", "COIN COLLECTED! Total: " + coinsCollected);
    }

    public void setScore(int distance) {
        if (gameActive) {
            scoreManager.setScore(distance);
        }
    }

    public int getScore() {
        return scoreManager.getScore();
    }

    public int getCoins() {
        return coinsCollected;
    }

    public void addObserver(com.HafizhAkbarGhifarieRamadhan.frontend.observers.Observer observer) {
        scoreManager.addObserver(observer);
    }

    public void removeObserver(com.HafizhAkbarGhifarieRamadhan.frontend.observers.Observer observer) {
        scoreManager.removeObserver(observer);
    }
}
