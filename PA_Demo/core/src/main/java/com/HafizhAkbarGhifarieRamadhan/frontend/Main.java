package com.HafizhAkbarGhifarieRamadhan.frontend;

import com.badlogic.gdx.Game;
import com.HafizhAkbarGhifarieRamadhan.frontend.screens.MenuScreen;

public class Main extends Game {

    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }
}


