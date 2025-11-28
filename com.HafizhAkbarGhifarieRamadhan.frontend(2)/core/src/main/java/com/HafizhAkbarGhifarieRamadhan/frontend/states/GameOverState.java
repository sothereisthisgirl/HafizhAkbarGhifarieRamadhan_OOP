package com.HafizhAkbarGhifarieRamadhan.frontend.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.HafizhAkbarGhifarieRamadhan.frontend.GameManager;
import com.HafizhAkbarGhifarieRamadhan.frontend.observers.ScoreManager;


public class GameOverState implements GameState {
    private final GameStateManager gsm;
    private final BitmapFont font;
    private final SpriteBatch spriteBatch;

    public GameOverState(GameStateManager gsm) {
        this.gsm = gsm;
        this.font = new BitmapFont();
        this.spriteBatch = new SpriteBatch();
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.justTouched()) {
            gsm.set(new MenuState(gsm));
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        ScreenUtils.clear(0.2f, 0, 0, 1);
        spriteBatch.begin();
        font.setColor(Color.WHITE);
        font.getData().setScale(2);
        font.draw(spriteBatch, "GAME OVER", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 1.5f, 0, Align.center, false);
        font.getData().setScale(1.5f);
        font.draw(spriteBatch, "Score: " + GameManager.getInstance().getScore(), Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0, Align.center, false);
        font.getData().setScale(1);
        font.draw(spriteBatch, "Tap to Return to Menu", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 3f, 0, Align.center, false);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        font.dispose();
        spriteBatch.dispose();
    }
}
