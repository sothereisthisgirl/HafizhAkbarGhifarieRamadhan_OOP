package com.HafizhAkbarGhifarieRamadhan.frontend.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.HafizhAkbarGhifarieRamadhan.frontend.entities.Character;
import com.HafizhAkbarGhifarieRamadhan.frontend.state.*;
import com.HafizhAkbarGhifarieRamadhan.frontend.strategy.*;

public class PlayerInputProcessor extends InputAdapter {

    private final Character player;

    public PlayerInputProcessor(Character player) {
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode) {

        switch (keycode) {
            case Input.Keys.W -> player.setState(new WalkingState());
            case Input.Keys.R -> player.setState(new RunningState());
            case Input.Keys.S -> player.setState(new StandingState());

            case Input.Keys.NUM_1 -> player.setStyle(new AggressiveStyle());
            case Input.Keys.NUM_2 -> player.setStyle(new DefensiveStyle());

            case Input.Keys.SPACE -> player.attack();
        }

        return true;
    }
}

