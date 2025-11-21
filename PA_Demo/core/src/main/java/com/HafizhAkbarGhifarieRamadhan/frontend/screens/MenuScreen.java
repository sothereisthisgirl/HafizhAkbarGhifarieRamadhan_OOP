package com.HafizhAkbarGhifarieRamadhan.frontend.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.HafizhAkbarGhifarieRamadhan.frontend.Main;

public class MenuScreen implements Screen {

    private final Main game;
    private SpriteBatch batch;
    private BitmapFont font;

    public MenuScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "Press SPACE to Start Game", 100, 200);
        batch.end();

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
        }
    }

    @Override public void dispose() { batch.dispose(); font.dispose(); }
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}


