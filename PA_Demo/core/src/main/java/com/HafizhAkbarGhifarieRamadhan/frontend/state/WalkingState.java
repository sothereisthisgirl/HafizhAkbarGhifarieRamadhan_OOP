package com.HafizhAkbarGhifarieRamadhan.frontend.state;

import com.badlogic.gdx.Gdx;
import com.HafizhAkbarGhifarieRamadhan.frontend.entities.Character;

public class WalkingState implements CharacterState {

    @Override
    public void update(Character character, float dt) {
        Gdx.app.log("State", "Character is walking...");
    }
}

