package com.HafizhAkbarGhifarieRamadhan.frontend.pools;

import com.badlogic.gdx.math.Vector2;
import com.HafizhAkbarGhifarieRamadhan.frontend.obstacles.VerticalLaser;

public class VerticalLaserPool extends ObjectPool<VerticalLaser> {

    @Override
    protected VerticalLaser createObject() {
        return new VerticalLaser(new Vector2(), 100);
    }

    @Override
    protected void resetObject(VerticalLaser object) {
        object.setPosition(com.badlogic.gdx.Gdx.graphics.getWidth(), 0);
        object.setActive(false);
    }

    public VerticalLaser obtain(Vector2 position, int length) {
        VerticalLaser laser = super.obtain();
        laser.initialize(position, length);
        laser.setActive(true);
        return laser;
    }
}
