package com.HafizhAkbarGhifarieRamadhan.frontend.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.HafizhAkbarGhifarieRamadhan.frontend.Main;

public class GameScreen implements Screen {

    private final Main game;
    private ShapeRenderer shape;
    private float x = 200;
    private float y = 150;

    // animation timers
    private float legTimer = 0;
    private float armTimer = 0;

    // attack state
    private boolean attacking = false;

    public GameScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        shape = new ShapeRenderer();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // movement
        float speed = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) ? 300 : 150; // run faster

        if (Gdx.input.isKeyPressed(Input.Keys.A)) x -= speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) x += speed * delta;

        // attack
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            attacking = true;
            armTimer = 0;
        }

        // animate legs when moving
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            legTimer += delta * (speed > 200 ? 8 : 4); // run = faster animation
        }

        // animate attack arm
        if (attacking) {
            armTimer += delta * 5;
            if (armTimer > 1) attacking = false;
        }

        drawStickman();
    }

    private void drawStickman() {
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.WHITE);

        // torso
        shape.line(x, y + 20, x, y + 60);

        // head
        shape.circle(x, y + 75, 15);

        // legs
        float legAngle = (float)Math.sin(legTimer) * 15;
        shape.line(x, y + 20, x - legAngle, y);
        shape.line(x, y + 20, x + legAngle, y);

        // arms
        if (attacking) {
            float attackAngle = (float)Math.sin(armTimer * 3) * 30;
            shape.line(x, y + 50, x + attackAngle, y + 40);
        } else {
            shape.line(x, y + 50, x + 20, y + 40); // normal arm
        }

        shape.line(x, y + 50, x - 20, y + 40); // left arm

        shape.end();
    }

    @Override public void dispose() { shape.dispose(); }
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
