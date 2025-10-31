package com.HafizhAkbarGhifarieRamadhan.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;

public class Game extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private float boxX, boxY;
    private float boxSize = 100;
    private Color currentColor;
    private Color[] colors = {Color.RED, Color.YELLOW, Color.BLUE};
    private int colorIndex = 0;
    private float speed = 300f;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        boxX = (Gdx.graphics.getWidth() - boxSize) / 2f;
        boxY = (Gdx.graphics.getHeight() - boxSize) / 2f;
        currentColor = colors[colorIndex];
    }

    @Override
    public void render() {
        handleInput();

        // Background black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(currentColor);
        shapeRenderer.rect(boxX, boxY, boxSize, boxSize);
        shapeRenderer.end();

        if (Gdx.input.justTouched()) {
            cycleColor();
        }
    }

    private void handleInput() {
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
            boxX -= speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
            boxX += speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))
            boxY += speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))
            boxY -= speed * delta;

        // Keep box inside the screen
        boxX = Math.max(0, Math.min(boxX, Gdx.graphics.getWidth() - boxSize));
        boxY = Math.max(0, Math.min(boxY, Gdx.graphics.getHeight() - boxSize));
    }

    private void cycleColor() {
        colorIndex = (colorIndex + 1) % colors.length;
        currentColor = colors[colorIndex];
        System.out.println("Color changed to: " + getColorName(currentColor));
    }

    private String getColorName(Color color) {
        if (color.equals(Color.RED)) return "Red";
        if (color.equals(Color.YELLOW)) return "Yellow";
        if (color.equals(Color.BLUE)) return "Blue";
        return "Unknown";
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
