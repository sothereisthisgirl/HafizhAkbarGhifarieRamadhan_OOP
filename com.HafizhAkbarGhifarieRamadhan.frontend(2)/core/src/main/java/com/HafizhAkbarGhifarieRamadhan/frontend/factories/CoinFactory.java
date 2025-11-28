package com.HafizhAkbarGhifarieRamadhan.frontend.factories;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Vector2;
import com.HafizhAkbarGhifarieRamadhan.frontend.Coin;

public class CoinFactory {

    private final Pool<Coin> coinPool;
    private final Array<Coin> activeCoins;

    public CoinFactory() {
        activeCoins = new Array<>();

        coinPool = new Pool<Coin>() {
            @Override
            protected Coin newObject() {
                // Coin requires a Vector2 in the constructor
                return new Coin(new Vector2(0, 0));
            }
        };
    }

    public Array<Coin> getActiveCoins() {
        return activeCoins;
    }

    public Coin obtainCoin(float x, float y) {
        Coin coin = coinPool.obtain();

        // match your real coin methods
        coin.setPosition(x, y);
        coin.setActive(true);

        activeCoins.add(coin);
        return coin;
    }

    public void releaseCoin(Coin coin) {
        coin.setActive(false);
        activeCoins.removeValue(coin, true);
        coinPool.free(coin);
    }

    public void releaseAll() {
        for (Coin c : activeCoins) {
            c.setActive(false);
            coinPool.free(c);
        }
        activeCoins.clear();
    }

    // Simple coin line pattern
    public void createCoinPattern(float baseX, float groundY) {
        for (int i = 0; i < 5; i++) {
            obtainCoin(baseX + i * 40f, groundY + 200f);
        }
    }
}

