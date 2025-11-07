package com.HafizhAkbarGhifarieRamadhan.frontend.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.HafizhAkbarGhifarieRamadhan.frontend.obstacles.BaseObstacle;
import com.HafizhAkbarGhifarieRamadhan.frontend.obstacles.HomingMissile;
import com.HafizhAkbarGhifarieRamadhan.frontend.obstacles.HorizontalLaser;
import com.HafizhAkbarGhifarieRamadhan.frontend.obstacles.VerticalLaser;
import com.HafizhAkbarGhifarieRamadhan.frontend.pools.HomingMissilePool;
import com.HafizhAkbarGhifarieRamadhan.frontend.pools.HorizontalLaserPool;
import com.HafizhAkbarGhifarieRamadhan.frontend.pools.VerticalLaserPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ObstacleFactory {
    /** Factory Method implementor */
    public interface ObstacleCreator {
        BaseObstacle create(float groundTopY, float spawnX, float playerHeight, Random rng);
        void release(BaseObstacle obstacle);
        void releaseAll();
        List<? extends BaseObstacle> getInUse();
        boolean supports(BaseObstacle obstacle);
        String getName();
    }

    // Inner creator classes
    public static class HorizontalLaserCreator implements ObstacleCreator {
        private final HorizontalLaserPool pool = new HorizontalLaserPool();
        private static final float MIN_LENGTH = 100f;
        private static final float MAX_LENGTH = 300f;

        @Override
        public BaseObstacle create(float groundTopY, float spawnX, float playerHeight, Random rng) {
            float obstacleSize = MIN_LENGTH + rng.nextFloat() * (MAX_LENGTH - MIN_LENGTH);
            float minY = groundTopY + playerHeight;
            float maxY = Gdx.graphics.getHeight() - playerHeight;
            float randomY = minY + rng.nextFloat() * (maxY - minY);

            return pool.obtain(new Vector2(spawnX, randomY), (int) obstacleSize);
        }

        @Override
        public void release(BaseObstacle obstacle) {
            if (obstacle instanceof HorizontalLaser) {
                pool.release((HorizontalLaser) obstacle);
            }
        }

        @Override
        public void releaseAll() {
            pool.releaseAll();
        }

        @Override
        @SuppressWarnings("unchecked")
        public List<HorizontalLaser> getInUse() {
            return pool.getInUse();
        }

        @Override
        public boolean supports(BaseObstacle obstacle) {
            return obstacle instanceof HorizontalLaser;
        }

        @Override
        public String getName() {
            return "HorizontalLaser";
        }
    }

    public static class VerticalLaserCreator implements ObstacleCreator {
        private final VerticalLaserPool pool = new VerticalLaserPool();
        private static final float MIN_HEIGHT = 100f;
        private static final float MAX_HEIGHT = 300f;

        @Override
        public BaseObstacle create(float groundTopY, float spawnX, float playerHeight, Random rng) {
            float obstacleSize = MIN_HEIGHT + rng.nextFloat() * (MAX_HEIGHT - MIN_HEIGHT);
            float randomY = groundTopY + rng.nextFloat() * (Gdx.graphics.getHeight() - groundTopY - obstacleSize);

            return pool.obtain(new Vector2(spawnX, randomY), (int) obstacleSize);
        }

        @Override
        public void release(BaseObstacle obstacle) {
            if (obstacle instanceof VerticalLaser) {
                pool.release((VerticalLaser) obstacle);
            }
        }

        @Override
        public void releaseAll() {
            pool.releaseAll();
        }

        @Override
        @SuppressWarnings("unchecked")
        public List<VerticalLaser> getInUse() {
            return pool.getInUse();
        }

        @Override
        public boolean supports(BaseObstacle obstacle) {
            return obstacle instanceof VerticalLaser;
        }

        @Override
        public String getName() {
            return "VerticalLaser";
        }
    }

    public static class HomingMissileCreator implements ObstacleCreator {
        private final HomingMissilePool pool = new HomingMissilePool();

        @Override
        public BaseObstacle create(float groundTopY, float spawnX, float playerHeight, Random rng) {
            float randomY = groundTopY + rng.nextFloat() * (Gdx.graphics.getHeight() - groundTopY);
            return pool.obtain(new Vector2(spawnX, randomY));
        }

        @Override
        public void release(BaseObstacle obstacle) {
            if (obstacle instanceof HomingMissile) {
                pool.release((HomingMissile) obstacle);
            }
        }

        @Override
        public void releaseAll() {
            pool.releaseAll();
        }

        @Override
        @SuppressWarnings("unchecked")
        public List<HomingMissile> getInUse() {
            return pool.getInUse();
        }

        @Override
        public boolean supports(BaseObstacle obstacle) {
            return obstacle instanceof HomingMissile;
        }

        @Override
        public String getName() {
            return "HomingMissile";
        }
    }

    /** Weighted creator for probability-based spawning */
    private static class WeightedCreator {
        ObstacleCreator creator;
        int weight;
        WeightedCreator(ObstacleCreator creator, int weight) {
            this.creator = creator;
            this.weight = weight;
        }
    }

    private final List<WeightedCreator> weightedCreators = new ArrayList<>();
    private final Random random = new Random();
    private int totalWeight = 0;

    public ObstacleFactory() {
        register(new VerticalLaserCreator(), 2);
        register(new HorizontalLaserCreator(), 2);
        register(new HomingMissileCreator(), 1);
    }

    public void register(ObstacleCreator creator, int weight) {
        weightedCreators.add(new WeightedCreator(creator, weight));
        totalWeight += weight;
    }

    /** Factory Method using weighted random selection */
    public BaseObstacle createRandomObstacle(float groundTopY, float spawnX, float playerHeight) {
        if (weightedCreators.isEmpty()) {
            throw new IllegalStateException("No obstacle creators registered");
        }
        ObstacleCreator creator = selectWeightedCreator();
        return creator.create(groundTopY, spawnX, playerHeight, random);
    }

    private ObstacleCreator selectWeightedCreator() {
        int randomValue = random.nextInt(totalWeight);
        int currentWeight = 0;
        for (WeightedCreator wc : weightedCreators) {
            currentWeight += wc.weight;
            if (randomValue < currentWeight) {
                return wc.creator;
            }
        }
        return weightedCreators.get(0).creator;
    }

    public void releaseObstacle(BaseObstacle obstacle) {
        for (WeightedCreator wc : weightedCreators) {
            if (wc.creator.supports(obstacle)) {
                wc.creator.release(obstacle);
                return;
            }
        }
    }

    public void releaseAllObstacles() {
        for (WeightedCreator wc : weightedCreators) {
            wc.creator.releaseAll();
        }
    }

    public List<BaseObstacle> getAllInUseObstacles() {
        List<BaseObstacle> list = new ArrayList<>();
        for (WeightedCreator wc : weightedCreators) {
            list.addAll(wc.creator.getInUse());
        }
        return list;
    }

    public List<String> getRegisteredCreatorNames() {
        List<String> names = new ArrayList<>();
        for (WeightedCreator wc : weightedCreators) {
            names.add(wc.creator.getName());
        }
        return names;
    }
}
