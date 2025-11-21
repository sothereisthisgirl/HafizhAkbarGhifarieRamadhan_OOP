package com.HafizhAkbarGhifarieRamadhan.frontend.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Stack;

public class GameStateManager {
    private final Stack<GameState> states;

    public GameStateManager(){
        states = new Stack<>();
    }
    public void push(GameState state){
        states.push(state);
    }
    public void pop(){
        GameState state = states.pop();
        state.dispose();
    }
}
