package com.HafizhAkbarGhifarieRamadhan.frontend.entities;

import com.badlogic.gdx.Gdx;
import com.HafizhAkbarGhifarieRamadhan.frontend.state.*;
import com.HafizhAkbarGhifarieRamadhan.frontend.strategy.*;

public class Character {

    private CharacterState state;
    private FightingStyle style;

    public Character() {
        this.state = new StandingState();
        this.style = new DefensiveStyle();
    }

    public void setState(CharacterState newState) {
        Gdx.app.log("Character", "State changed to " + newState.getClass().getSimpleName());
        this.state = newState;
    }

    public void setStyle(FightingStyle newStyle) {
        Gdx.app.log("Character", "Style changed to " + newStyle.getName());
        this.style = newStyle;
    }

    public void update(float dt) {
        state.update(this, dt);
    }

    public void attack() {
        style.fight();
    }
}
