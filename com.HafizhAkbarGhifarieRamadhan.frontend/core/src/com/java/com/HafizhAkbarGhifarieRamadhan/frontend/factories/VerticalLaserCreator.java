package com.java.com.HafizhAkbarGhifarieRamadhan.frontend.factories;

import java.util.List;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.java.com.HafizhAkbarGhifarieRamadhan.frontend.obstacles.BaseObstacle;
import com.java.com.HafizhAkbarGhifarieRamadhan.frontend.obstacles.VerticalLaser;
import com.java.com.HafizhAkbarGhifarieRamadhan.frontend.pools.VerticalLaserPool;

public class VerticalLaserCreator implements ObstacleFactory.ObstacleCreator {
    private final VerticalLaserPool pool = new VerticalLaserPool();
    private static final float MIN_HEIGHT = 100f;
    private static final float MAX_HEIGHT = 300f;

    @Override
    public BaseObstacle create(float groundTopY, float spawnX, float playerHeight, Random rng) {
        float obstacleSize = MIN_HEIGHT + rng.nextFloat() * (MAX_HEIGHT - MIN_HEIGHT);
        float randomY = groundTopY + rng.nextFloat() * (Gdx.graphics.getHeight() - groundTopY - obstacleSize);

        return pool.obtain(new Vector2(spawnX, randomY), (int) obstacleSize);
    }

    @Override
    public void release(BaseObstacle obstacle) {
        if (obstacle instanceof VerticalLaser) {
            pool.release((VerticalLaser) obstacle);
        }
    }

    @Override
    public void releaseAll() {
        pool.releaseAll();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<VerticalLaser> getInUse() {
        return pool.getInUse();
    }

    @Override
    public boolean supports(BaseObstacle obstacle) {
        return obstacle instanceof VerticalLaser;
    }

    @Override
    public String getName() {
        return "VerticalLaser";
    }
}
