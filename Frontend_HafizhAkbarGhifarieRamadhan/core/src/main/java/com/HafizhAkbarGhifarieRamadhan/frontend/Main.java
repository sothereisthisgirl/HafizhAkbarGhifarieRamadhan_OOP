package com.HafizhAkbarGhifarieRamadhan.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font;
    private Player player;
    private Ground ground;
    private GameManager gameManager;
    private OrthographicCamera camera;
    private float cameraOffset = 0.2f;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont(); // default font
        font.setColor(Color.BLACK);

        gameManager = GameManager.getInstance();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false);

        ground = new Ground();
        player = new Player(new Vector2(100, Gdx.graphics.getHeight() / 2f), ground, Gdx.graphics.getHeight());
        gameManager.startGame();
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        update(delta);

        ScreenUtils.clear(0.3f, 0.7f, 1f, 1f); // sky-blue background

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        ground.renderShape(shapeRenderer);
        player.renderShape(shapeRenderer);
        shapeRenderer.end();

        // Draw score text
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Score: " + gameManager.getScore(), camera.position.x - Gdx.graphics.getWidth() / 2f + 20,
            Gdx.graphics.getHeight() - 20);
        batch.end();
    }

    private void update(float delta) {
        boolean isFlying = Gdx.input.isKeyPressed(Input.Keys.SPACE);
        player.update(delta, isFlying);
        player.checkBoundaries();

        ground.update(delta, camera);
        updateCamera();

        int newScore = (int) player.getPosition().x;
        if (newScore != gameManager.getScore()) {
            gameManager.setScore(newScore);
            System.out.println("Score: " + newScore);
        }
    }

    private void updateCamera() {
        camera.position.x = player.getPosition().x + (cameraOffset * Gdx.graphics.getWidth());
        camera.update();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();
    }
}

