package com.HafizhAkbarGhifarieRamadhan.frontend.strategy;

import com.badlogic.gdx.Gdx;

public class DefensiveStyle implements FightingStyle {

    @Override
    public void fight() {
        Gdx.app.log("Style", "Defensive blocking stance.");
    }

    @Override
    public String getName() {
        return "Defensive";
    }
}

