package com.HafizhAkbarGhifarieRamadhan.frontend;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Coin {

    private Vector2 position;
    private Rectangle collider;
    private float radius = 15f;
    private boolean active = false;

    private float bobOffset = 0f;
    private float bobSpeed = 3f;

    
    public Coin(Vector2 startPosition) {
        this.position = new Vector2(startPosition);
        this.collider = new Rectangle(position.x - radius, position.y - radius, radius * 2, radius * 2);
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
        this.collider.setPosition(x - radius, y - radius);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void update(float delta) {
        if (!active) return;

        bobOffset += bobSpeed * delta;

        float bobY = (float) Math.sin(bobOffset) * 5f;
        collider.setPosition(position.x - radius, (position.y + bobY) - radius);
    }

    public void renderShape(ShapeRenderer shapeRenderer) {
        if (!active) return;

        float drawY = position.y + (float) Math.sin(bobOffset) * 5f;

        shapeRenderer.setColor(1f, 1f, 0f, 1f); 
        shapeRenderer.circle(position.x, drawY, radius);
    }

    public boolean isColliding(Rectangle playerCollider) {
        return active && collider.overlaps(playerCollider);
    }

    public boolean isOffScreenCamera(float cameraLeftX) {
    return position.x + radius < cameraLeftX;
}

}
