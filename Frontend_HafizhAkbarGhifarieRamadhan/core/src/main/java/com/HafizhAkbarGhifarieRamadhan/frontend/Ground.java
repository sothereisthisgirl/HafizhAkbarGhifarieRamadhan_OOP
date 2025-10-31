package com.HafizhAkbarGhifarieRamadhan.frontend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Ground {
    float GROUND_HEIGHT = 50f;
    private Rectangle collider;

    public Ground() {
        this.collider = new Rectangle(0, 0, Gdx.graphics.getWidth() * 2, GROUND_HEIGHT);
    }

    void update(float cameraX){
        float groundWidth = Gdx.graphics.getWidth() * 2;
        float xAxis = cameraX - Gdx.graphics.getWidth() / 2f - 500;
        float yAxis = 0;
        collider.setWidth(groundWidth);
    }
    public boolean isColliding(Rectangle playerCollider){
        return collider.overlaps(playerCollider);
    }
        public float getTopY(){
        return GROUND_HEIGHT;
    }

    public void renderShape(ShapeRenderer shapeRenderer){
        shapeRenderer.setColor(0.5f, 0,5f, 0,5f, 1f);
        shapeRenderer.rect(collider.x, collider.y, collider.width, collider.height);

    }
}
