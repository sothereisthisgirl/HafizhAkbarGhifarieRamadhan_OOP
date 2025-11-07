package com.java.com.HafizhAkbarGhifarieRamadhan.frontend;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.java.com.HafizhAkbarGhifarieRamadhan.frontend.factories.ObstacleFactory;
import com.HafizhAkbarGhifarieRamadhan.frontend.obstacle.BaseObstacle;
import com.HafizhAkbarGhifarieRamadhan.frontend.obstacle.HomingMissile;

public class Main extends Game {
    private ShapeRenderer shapeRenderer;
    private Player player;
    private Ground ground;
    private GameManager gameManager;
    private ObstacleFactory obstacleFactory;
    private float obstacleSpawnTimer;
    private float lastObstacleSpawnX = 0f;
    private static final float OBSTACLE_SPAWN_INTERVAL = 2.5f;
    private static final int   OBSTACLE_DENSITY       = 1;
    private static final float SPAWN_AHEAD_DISTANCE   = 300f;
    private static final float MIN_OBSTACLE_GAP       = 200f;
    private static final float OBSTACLE_CLUSTER_SPACING = 250f;
    private OrthographicCamera camera;
    private float cameraOffset = 0.2f;
    private int screenWidth;
    private int screenHeight;

    private int lastLoggedScore = -1;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        this.gameManager = GameManager.getInstance();

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);

        player = new Player(new Vector2(100f, screenHeight / 2f));
        ground = new Ground();

        obstacleFactory = new ObstacleFactory();
        obstacleSpawnTimer = 0f;

        gameManager.startGame();
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        update(delta);
        renderGame(shapeRenderer);
    }

    private void update(float delta) {
        boolean isFlying = Gdx.input.isKeyPressed(Input.Keys.SPACE);

        // restart when dead + SPACE
        if (player.isDead() && isFlying) {
            resetGame();
            return;
        }

        player.update(delta, isFlying);
        updateCamera(delta);
        ground.update(camera.position.x);
        player.checkBoundaries(ground, screenHeight);

        updateObstacles(delta);
        checkCollisions();

        // score by distance (meters)
        int currentScoreMeters = (int) player.getDistanceTraveled();
        int previousScoreMeters = gameManager.getScore();
        if (currentScoreMeters > previousScoreMeters) {
            if (currentScoreMeters != lastLoggedScore) {
                System.out.println("Distance: " + currentScoreMeters + "m");
                lastLoggedScore = currentScoreMeters;
            }
            gameManager.setScore(currentScoreMeters);
        }
    }

    private void renderGame(ShapeRenderer sr) {
        ScreenUtils.clear(0.08f, 0.08f, 0.1f, 1f);
        sr.setProjectionMatrix(camera.combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);

        ground.renderShape(sr);
        player.renderShape(sr);

        sr.setColor(Color.RED);
        for (BaseObstacle o : obstacleFactory.getAllInUseObstacles()) {
            o.render(sr);
        }

        sr.end();
    }

    private void updateCamera(float delta) {
        float cameraFocus = player.getPosition().x + screenWidth * cameraOffset;
        camera.position.x = cameraFocus;
        camera.update();
    }

    private void updateObstacles(float delta) {
        obstacleSpawnTimer += delta;
        if (obstacleSpawnTimer >= OBSTACLE_SPAWN_INTERVAL) {
            spawnObstacle();
            obstacleSpawnTimer = 0f;
        }

        float cameraLeftEdge = camera.position.x - screenWidth / 2f;
        for (BaseObstacle o : obstacleFactory.getAllInUseObstacles()) {
            if (o instanceof HomingMissile) {
                HomingMissile m = (HomingMissile) o;
                m.setTarget(player);
                m.update(delta);
            }
            if (o.isOffScreenCamera(cameraLeftEdge)) {
                obstacleFactory.releaseObstacle(o);
            }
        }
    }

    private void spawnObstacle() {
        float cameraRightEdge = camera.position.x + screenWidth / 2f;
        float spawnAheadOfCamera = cameraRightEdge + SPAWN_AHEAD_DISTANCE;
        float spawnAfterLastObstacle = lastObstacleSpawnX + MIN_OBSTACLE_GAP;
        float baseSpawnX = Math.max(spawnAheadOfCamera, spawnAfterLastObstacle);

        for (int i = 0; i < OBSTACLE_DENSITY; i++) {
            float spawnX = baseSpawnX + i * OBSTACLE_CLUSTER_SPACING;
            BaseObstacle o = obstacleFactory.createRandomObstacle(ground.getTopY(), spawnX, player.getHeight());
            lastObstacleSpawnX = spawnX;
            // optional: tweak per-type after creation if needed
        }
    }

    private void checkCollisions() {
        Rectangle pc = player.getCollider();
        for (BaseObstacle o : obstacleFactory.getAllInUseObstacles()) {
            if (o.isColliding(pc)) {
                System.out.println("GAME OVER — press SPACE to restart");
                player.die();
                return;
            }
        }
    }

    private void resetGame() {
        player.reset();
        obstacleFactory.releaseAllObstacles();
        obstacleSpawnTimer = 0f;
        lastObstacleSpawnX = 0f;

        camera.position.set(player.getPosition().x + screenWidth * cameraOffset, screenHeight / 2f, 0f);
        camera.update();

        gameManager.setScore(0);
        lastLoggedScore = -1;

        System.out.println("Game reset!");
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        obstacleFactory.releaseAllObstacles();
    }
}
