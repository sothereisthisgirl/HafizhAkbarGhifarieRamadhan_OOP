package com.HafizhAkbarGhifarieRamadhan.frontend.strategy;

import com.badlogic.gdx.Gdx;

public class AggressiveStyle implements FightingStyle {

    @Override
    public void fight() {
        Gdx.app.log("Style", "Aggressive attack!");
    }

    @Override
    public String getName() {
        return "Aggressive";
    }
}
