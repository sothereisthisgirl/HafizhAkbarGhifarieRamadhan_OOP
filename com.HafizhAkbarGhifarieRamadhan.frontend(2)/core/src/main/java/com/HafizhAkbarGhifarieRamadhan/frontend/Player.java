package com.HafizhAkbarGhifarieRamadhan.frontend;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private Vector2 position;
    private Vector2 startPosition;
    private Vector2 velocity;
    private float gravity = 2000f;
    private float force = 4500f;
    private float maxVerticalSpeed = 700f;

    private Rectangle collider;
    private float width = 64f;
    private float height = 64f;

    private float baseSpeed = 300f;
    private float distanceTraveled = 0f;

    // death state
    private boolean isDead = false;

    public Player(Vector2 startPosition) {
        this.position = new Vector2(startPosition);
        this.startPosition = new Vector2(startPosition);
        this.velocity = new Vector2(baseSpeed, 0f);
        this.collider = new Rectangle(position.x, position.y, width, height);
    }

    public void update(float delta, boolean isFlying) {
        if (isDead) return; // only move when alive

        updateDistanceAndSpeed(delta);
        updatePosition(delta);
        applyGravity(delta);
        if (isFlying) fly(delta);
        updateCollider();
    }

    private void updateDistanceAndSpeed(float delta) {
        distanceTraveled += Math.abs(velocity.x) * delta;
    }

    private void updatePosition(float delta) {
        position.x += velocity.x * delta;
        position.y += velocity.y * delta;
    }

    private void applyGravity(float delta) {
        velocity.y -= gravity * delta;
        velocity.x = baseSpeed;

        if (velocity.y >  maxVerticalSpeed)  velocity.y =  maxVerticalSpeed;
        if (velocity.y < -maxVerticalSpeed)  velocity.y = -maxVerticalSpeed;
    }

    private void fly(float delta) { velocity.y += force * delta; }

    private void updateCollider() { collider.set(position.x, position.y, width, height); }

    public void checkBoundaries(Ground ground, float ceilingY) {
        if (ground.isColliding(collider)) {
            position.y = ground.getTopY();
            velocity.y = 0f;
            updateCollider();
        }
        if (position.y + height > ceilingY) {
            position.y = ceilingY - height;
            velocity.y = 0f;
            updateCollider();
        }
    }

    public void renderShape(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(isDead ? 1f : 0.2f, isDead ? 0f : 0.8f, 0.3f, 1f);
        shapeRenderer.rect(position.x, position.y, width, height);
    }

    // death/reset
    public void die() {
        isDead = true;
        velocity.set(0f, 0f);
    }

    public void reset() {
        isDead = false;
        position.set(startPosition);
        velocity.set(baseSpeed, 0f);
        distanceTraveled = 0f;
        updateCollider();
    }

    public boolean isDead() { return isDead; }

    // getters
    public Vector2 getPosition() { return position; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public Rectangle getCollider() { return collider; }
    public float getDistanceTraveled() { return distanceTraveled / 10f; }
}
