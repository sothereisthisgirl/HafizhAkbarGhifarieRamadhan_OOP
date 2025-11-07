package com.java.com.HafizhAkbarGhifarieRamadhan.frontend.factories;

import java.util.List;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.java.com.HafizhAkbarGhifarieRamadhan.frontend.obstacles.BaseObstacle;
import com.java.com.HafizhAkbarGhifarieRamadhan.frontend.obstacles.HomingMissile;
import com.java.com.HafizhAkbarGhifarieRamadhan.frontend.pools.HomingMissilePool;

public class HomingMissileCreator implements ObstacleFactory.ObstacleCreator {
    private final HomingMissilePool pool = new HomingMissilePool();

    @Override
    public BaseObstacle create(float groundTopY, float spawnX, float playerHeight, Random rng) {
        float randomY = groundTopY + rng.nextFloat() * (Gdx.graphics.getHeight() - groundTopY);
        return pool.obtain(new Vector2(spawnX, randomY));
    }

    @Override
    public void release(BaseObstacle obstacle) {
        if (obstacle instanceof HomingMissile) {
            pool.release((HomingMissile) obstacle);
        }
    }

    @Override
    public void releaseAll() {
        pool.releaseAll();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<HomingMissile> getInUse() {
        return pool.getInUse();
    }

    @Override
    public boolean supports(BaseObstacle obstacle) {
        return obstacle instanceof HomingMissile;
    }

    @Override
    public String getName() {
        return "HomingMissile";
    }
}
