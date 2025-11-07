package com.java.com.HafizhAkbarGhifarieRamadhan.frontend;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
    // all stats
    private Vector2 position;
    private Vector2 velocity;
    private float gravity = 2000f;
    private float force = 4500f;
    private float maxVerticalSpeed = 700f;
    private Rectangle collider;
    private float width = 64f;
    private float height = 64f;

    // speed
    private float baseSpeed = 300f;
    private float distanceTraveled = 0f;

    // Death system
    private boolean isDead = false;
    private Vector2 startPosition;

    // constructor - UPDATED: Removed ground and ceiling parameters
    public Player(Vector2 startPosition) {
        this.startPosition = new Vector2(startPosition);
        this.position = new Vector2(startPosition);
        this.velocity = new Vector2(baseSpeed, 0);
        this.collider = new Rectangle(position.x, position.y, width, height);
    }

    // update
    public void update(float delta, boolean isFlying) {
        if (isDead) return;

        updateDistanceAndSpeed(delta);
        applyGravity(delta);
        if (isFlying) fly(delta);
        updatePosition(delta);
        updateCollider();
    }

    // update distance and speed
    private void updateDistanceAndSpeed(float delta) {
        distanceTraveled += velocity.x * delta;
    }

    private void updatePosition(float delta) {
        position.x += velocity.x * delta;
        position.y += velocity.y * delta;
    }

    private void applyGravity(float delta) {
        velocity.y -= gravity * delta;
        velocity.x = baseSpeed;

        if (velocity.y > maxVerticalSpeed)
            velocity.y = maxVerticalSpeed;
        if (velocity.y < -maxVerticalSpeed)
            velocity.y = -maxVerticalSpeed;
    }

    private void fly(float delta) {
        velocity.y += force * delta; // upward thrust
    }

    private void updateCollider() {
        collider.setPosition(position.x, position.y);
    }

    // boundaries
    public void checkBoundaries(Ground ground, float screenHeight) {
        if (isDead) return;

        // Ground collision
        if (ground.isColliding(collider)) {
            position.y = ground.getTopY();
            velocity.y = 0;
            updateCollider();
        }

        // ceiling - using screenHeight
        if (position.y + height > screenHeight) {
            position.y = screenHeight - height;
            velocity.y = 0;
            updateCollider();
        }
    }

    // render
    public void render(ShapeRenderer shapeRenderer) {
        if (isDead) return;
        shapeRenderer.setColor(Color.SKY);
        shapeRenderer.rect(position.x, position.y, width, height);
    }

    // Death and reset methods - ADDED
    public void die() {
        isDead = true;
        velocity.set(0, 0);
    }

    public void reset() {
        isDead = false;
        position.set(startPosition);
        velocity.set(baseSpeed, 0);
        distanceTraveled = 0f;
        updateCollider();
    }

    public boolean isDead() {
        return isDead;
    }

    // Getters
    public Vector2 getPosition() {
        return position;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Rectangle getCollider() {
        return collider;
    }

    public float getDistanceTraveled() {
        return distanceTraveled / 10f; // convert to game units
    }
}
