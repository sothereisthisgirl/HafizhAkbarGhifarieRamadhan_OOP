package com.HafizhAkbarGhifarieRamadhan.frontend.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.HafizhAkbarGhifarieRamadhan.frontend.Background;
import com.HafizhAkbarGhifarieRamadhan.frontend.GameManager;
import com.HafizhAkbarGhifarieRamadhan.frontend.Ground;
import com.HafizhAkbarGhifarieRamadhan.frontend.Player;
import com.HafizhAkbarGhifarieRamadhan.frontend.Coin;
import com.HafizhAkbarGhifarieRamadhan.frontend.commands.Command;
import com.HafizhAkbarGhifarieRamadhan.frontend.commands.JetpackCommand;
import com.HafizhAkbarGhifarieRamadhan.frontend.factories.CoinFactory;
import com.HafizhAkbarGhifarieRamadhan.frontend.factories.ObstacleFactory;
import com.HafizhAkbarGhifarieRamadhan.frontend.observers.ScoreUIObserver;
import com.HafizhAkbarGhifarieRamadhan.frontend.obstacles.BaseObstacle;
import com.HafizhAkbarGhifarieRamadhan.frontend.obstacles.HomingMissile;
import com.HafizhAkbarGhifarieRamadhan.frontend.strategies.DifficultyStrategy;
import com.HafizhAkbarGhifarieRamadhan.frontend.strategies.EasyDifficultyStrategy;
import com.HafizhAkbarGhifarieRamadhan.frontend.strategies.MediumDifficultyStrategy;
import com.HafizhAkbarGhifarieRamadhan.frontend.strategies.HardDifficultyStrategy;

public class PlayingState implements GameState {
    private final GameStateManager gsm;
    private final ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;

    private final Player player;
    private final Ground ground;
    private final Background background;
    private final Command jetpackCommand;
    private final ScoreUIObserver scoreUIObserver;
    private final ObstacleFactory obstacleFactory;

    private final CoinFactory coinFactory;
    private float coinSpawnTimer = 0f;

    private float obstacleSpawnTimer;
    private float lastObstacleSpawnX = 0f;

    private static final float SPAWN_AHEAD_DISTANCE = 300f;
    private static final float OBSTACLE_CLUSTER_SPACING = 250f;

    private final OrthographicCamera camera;
    private final float cameraOffset = 0.2f;

    private final int screenWidth;
    private final int screenHeight;
    private int lastLoggedScore = -1;

    private DifficultyStrategy difficultyStrategy;

    public PlayingState(GameStateManager gsm) {
        this.gsm = gsm;
        this.shapeRenderer = new ShapeRenderer();
        this.screenWidth = Gdx.graphics.getWidth();
        this.screenHeight = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);

        player = new Player(new Vector2(100, screenHeight / 2f));
        ground = new Ground();
        background = new Background();
        jetpackCommand = new JetpackCommand(player);

        scoreUIObserver = new ScoreUIObserver();
        GameManager.getInstance().addObserver(scoreUIObserver);

        obstacleFactory = new ObstacleFactory();
        coinFactory = new CoinFactory();

        setDifficulty(new EasyDifficultyStrategy());
        obstacleSpawnTimer = 0f;

        GameManager.getInstance().startGame();
    }

    public void setDifficulty(DifficultyStrategy newStrategy) {
        this.difficultyStrategy = newStrategy;
        this.obstacleFactory.setWeights(newStrategy.getObstacleWeights());
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            jetpackCommand.execute();
        }

        if (player.isDead()) {
            GameManager.getInstance().endGame();
            gsm.set(new GameOverState(gsm));
            return;
        }

        player.update(delta, false);
        updateCamera(delta);
        background.update(camera.position.x);
        ground.update(camera.position.x);
        player.checkBoundaries(ground, screenHeight);

        updateObstacles(delta);
        updateCoins(delta);
        checkCollisions();
        checkCoinCollisions();

        int currentScoreMeters = (int) player.getDistanceTraveled();
        GameManager.getInstance().setScore(currentScoreMeters);

        if (currentScoreMeters > lastLoggedScore) {
            lastLoggedScore = currentScoreMeters;
        }

        updateDifficulty(currentScoreMeters);
    }

    private void updateDifficulty(int score) {
        if (score > 1000 && !(difficultyStrategy instanceof HardDifficultyStrategy)) {
            if (score > 2000) {
                gsm.push(new DifficultyTransitionState(gsm, this, new HardDifficultyStrategy()));
            } else if (!(difficultyStrategy instanceof MediumDifficultyStrategy)) {
                gsm.push(new DifficultyTransitionState(gsm, this, new MediumDifficultyStrategy()));
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (spriteBatch == null) spriteBatch = new SpriteBatch();

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        background.render(spriteBatch);
        spriteBatch.end();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        player.renderShape(shapeRenderer);
        shapeRenderer.setColor(Color.RED);

        for (BaseObstacle o : obstacleFactory.getAllInUseObstacles()) o.render(shapeRenderer);
        for (Coin c : coinFactory.getActiveCoins()) c.renderShape(shapeRenderer);

        shapeRenderer.end();

        scoreUIObserver.render(
            GameManager.getInstance().getScore(),
            GameManager.getInstance().getCoins()
        );
    }

    private void updateCamera(float delta) {
        camera.position.x = player.getPosition().x + screenWidth * cameraOffset;
        camera.update();
    }

    private void updateObstacles(float delta) {
        obstacleSpawnTimer += delta;

        if (obstacleSpawnTimer >= difficultyStrategy.getSpawnInterval()) {
            spawnObstacle();
            obstacleSpawnTimer = 0f;
        }

        float left = camera.position.x - screenWidth / 2f;
        for (BaseObstacle obstacle : obstacleFactory.getAllInUseObstacles()) {
            if (obstacle instanceof HomingMissile) {
                ((HomingMissile) obstacle).setTarget(player);
                obstacle.update(delta);
            }
            if (obstacle.isOffScreenCamera(left)) obstacleFactory.releaseObstacle(obstacle);
        }
    }

    private void spawnObstacle() {
        float right = camera.position.x + screenWidth / 2f;
        float spawnBase = Math.max(right + SPAWN_AHEAD_DISTANCE, lastObstacleSpawnX + difficultyStrategy.getMinGap());

        for (int i = 0; i < difficultyStrategy.getDensity(); i++) {
            float x = spawnBase + i * OBSTACLE_CLUSTER_SPACING;
            obstacleFactory.createRandomObstacle(ground.getTopY(), x, player.getHeight());
            lastObstacleSpawnX = x;
        }
    }

    private void updateCoins(float delta) {
        coinSpawnTimer += delta;

        if (coinSpawnTimer > 0.5f) {
            float spawnX = camera.position.x + screenWidth;
            coinFactory.createCoinPattern(spawnX, ground.getTopY());
            coinSpawnTimer = 0f;
        }

        float left = camera.position.x - screenWidth / 2f;

        for (Coin coin : coinFactory.getActiveCoins()) {
            coin.update(delta);
            if (coin.isOffScreenCamera(left)) coinFactory.releaseCoin(coin);
        }
    }

    private void checkCollisions() {
        Rectangle col = player.getCollider();
        for (BaseObstacle o : obstacleFactory.getAllInUseObstacles()) {
            if (o.isColliding(col)) {
                player.die();
                return;
            }
        }
    }

    private void checkCoinCollisions() {
        Rectangle col = player.getCollider();

        for (Coin coin : coinFactory.getActiveCoins()) {
            if (coin.isColliding(col)) {
                coin.setActive(false);
                coinFactory.releaseCoin(coin);
                GameManager.getInstance().addCoin();
            }
        }
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        if (spriteBatch != null) spriteBatch.dispose();
        obstacleFactory.releaseAllObstacles();
        coinFactory.releaseAll();
        scoreUIObserver.dispose();
        background.dispose();
    }
}
