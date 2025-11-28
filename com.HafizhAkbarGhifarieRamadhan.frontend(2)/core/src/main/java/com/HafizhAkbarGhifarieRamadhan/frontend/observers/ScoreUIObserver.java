package com.HafizhAkbarGhifarieRamadhan.frontend.observers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
        Gdx.app.log("ScoreUI", "Score updated: " + score);
    }

    // Changed signature to accept only 'int score'
    public void render(int score, int coins)
    {
        batch.begin();
        // Removed the 'coins' parameter usage since it wasn't being passed
        font.draw(batch, "Score: " + score, 10, Gdx.graphics.getHeight() - 20);
        batch.end();
    }

    public void dispose() {
        font.dispose();
        batch.dispose();
    }
}
