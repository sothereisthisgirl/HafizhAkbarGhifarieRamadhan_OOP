package com.java.com.HafizhAkbarGhifarieRamadhan.frontend.pools;

import com.badlogic.gdx.math.Vector2;
import com.java.com.HafizhAkbarGhifarieRamadhan.frontend.obstacles.HorizontalLaser;

public class HorizontalLaserPool extends ObjectPool<HorizontalLaser> {

    @Override
    protected HorizontalLaser createObject() {
        return new HorizontalLaser(new Vector2(), 100);
    }

    @Override
    protected void resetObject(HorizontalLaser object) {
        object.setPosition(com.badlogic.gdx.Gdx.graphics.getWidth(), 0);
        object.setActive(false);
    }

    public HorizontalLaser obtain(Vector2 position, int length) {
        HorizontalLaser laser = super.obtain();
        laser.initialize(position, length);
        laser.setActive(true);
        return laser;
    }
}
