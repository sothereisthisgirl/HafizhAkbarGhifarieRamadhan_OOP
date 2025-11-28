package com.HafizhAkbarGhifarieRamadhan.frontend.factories;

import com.HafizhAkbarGhifarieRamadhan.frontend.Coin;
import com.HafizhAkbarGhifarieRamadhan.frontend.pools.CoinPool;
import java.util.ArrayList;
import java.util.Random;

public class CoinFactory {

    private final CoinPool coinPool;
    private final Random random;
    private final ArrayList<Coin> activeCoins;

    public CoinFactory() {
        this.coinPool = new CoinPool();
        this.random = new Random();
        this.activeCoins = new ArrayList<>();
    }

    public void createCoinPattern(float spawnX, float groundTopY) {
        if (random.nextFloat() <= 0.3f) {
            for (int i = 0; i < 3; i++) {
                Coin coin = coinPool.obtain(spawnX + (i * 40f), groundTopY + 120f);
                activeCoins.add(coin);
            }
        }
    }

    public ArrayList<Coin> getActiveCoins() {
        return activeCoins;
    }

    public void releaseCoin(Coin coin) {
        coin.setActive(false);
        coinPool.free(coin);
        activeCoins.remove(coin);
    }

    public void releaseAll() {
    for (Coin coin : new ArrayList<>(activeCoins)) {
        coin.setActive(false);
        coinPool.free(coin);
    }
    activeCoins.clear();
}

}