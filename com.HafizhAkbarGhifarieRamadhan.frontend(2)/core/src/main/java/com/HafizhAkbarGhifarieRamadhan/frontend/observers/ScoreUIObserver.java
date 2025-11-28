package com.HafizhAkbarGhifarieRamadhan.frontend.observers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Concrete observer that displays the score on screen
 */
public class ScoreUIObserver implements Observer {
    private BitmapFont font;
    private SpriteBatch batch;

    public ScoreUIObserver() {
        this.font = new BitmapFont();
        this.font.setColor(Color.WHITE);
        this.batch = new SpriteBatch();
    }

    @Override
    public void update(int score) {
        // In a real implementation, we would render the score to the screen
        // For now, we'll just print to console to demonstrate the observer pattern
        Gdx.app.log("ScoreUI", "Score updated: " + score);
    }

    public void render(int score, int coins) {
        batch.begin();
        font.draw(batch, "Score: " + score, 10, Gdx.graphics.getHeight() - 20);
        batch.end();
    }

    public void dispose() {
        font.dispose();
        batch.dispose();
    }
}
