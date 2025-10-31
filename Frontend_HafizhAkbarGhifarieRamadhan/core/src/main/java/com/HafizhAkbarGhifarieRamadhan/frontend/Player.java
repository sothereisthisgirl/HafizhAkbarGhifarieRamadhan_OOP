package com.HafizhAkbarGhifarieRamadhan.frontend;

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
    private Ground ground;
    private float ceilingY;

    // constructor
    public Player(Vector2 startPosition, Ground ground, float ceilingY) {
        this.position = new Vector2(startPosition);
        this.velocity = new Vector2(baseSpeed, 0);
        this.collider = new Rectangle(position.x, position.y, width, height);

        this.ground = ground;
        this.ceilingY = ceilingY;
    }

    // update
    public void update(float delta, boolean isFlying) {
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
    public void checkBoundaries() {
        // Ground collision
        if (ground.isColliding(collider)) {
            position.y = ground.getTopY();
            velocity.y = 0;
            updateCollider();
        }

        // ceiling
        if (position.y + height > ceilingY) {
            position.y = ceilingY - height;
            velocity.y = 0;
            updateCollider();
        }
    }

    // render
    public void renderShape(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.SKY);
        shapeRenderer.rect(position.x, position.y, width, height);
    }

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


