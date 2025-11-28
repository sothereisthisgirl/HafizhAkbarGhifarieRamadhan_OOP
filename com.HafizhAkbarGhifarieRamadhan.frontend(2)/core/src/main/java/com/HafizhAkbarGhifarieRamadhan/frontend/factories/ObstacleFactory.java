package com.HafizhAkbarGhifarieRamadhan.frontend.factories;

import java.util.*;
import com.HafizhAkbarGhifarieRamadhan.frontend.obstacles.BaseObstacle;

public class ObstacleFactory {

    public interface ObstacleCreator {
        BaseObstacle create(float groundTopY, float spawnX, float playerHeight, Random rng);
        void release(BaseObstacle obstacle);
        void releaseAll();
        List<? extends BaseObstacle> getInUse();
        boolean supports(BaseObstacle obstacle);
        String getName();
    }

    private final Map<String, ObstacleCreator> creators = new HashMap<>();
    private final List<ObstacleCreator> weightedSelection = new ArrayList<>();
    private final Random random = new Random();

    public ObstacleFactory() {
        register(new VerticalLaserCreator());
        register(new HorizontalLaserCreator());
        register(new HomingMissileCreator());
    }

    private void register(ObstacleCreator creator) {
        creators.put(creator.getName(), creator);
    }

    public void setWeights(Map<String, Integer> weights) {
        weightedSelection.clear();
        for (Map.Entry<String, Integer> e : weights.entrySet()) {
            String name = e.getKey();
            int weight = e.getValue();
            if (creators.containsKey(name)) {
                ObstacleCreator creator = creators.get(name);
                for (int i = 0; i < weight; i++) {
                    weightedSelection.add(creator);
                }
            }
        }
    }

    public BaseObstacle createRandomObstacle(float groundTopY, float spawnX, float playerHeight) {
        if (weightedSelection.isEmpty()) return null;
        ObstacleCreator creator = selectWeightedCreator();
        return creator.create(groundTopY, spawnX, playerHeight, random);
    }

    private ObstacleCreator selectWeightedCreator() {
        int index = random.nextInt(weightedSelection.size());
        return weightedSelection.get(index);
    }

    public void releaseObstacle(BaseObstacle obstacle) {
        for (ObstacleCreator creator : creators.values()) {
            if (creator.supports(obstacle)) {
                creator.release(obstacle);
                return;
            }
        }
    }

    public void releaseAllObstacles() {
        for (ObstacleCreator creator : creators.values()) {
            creator.releaseAll();
        }
    }

    public List<BaseObstacle> getAllInUseObstacles() {
        List<BaseObstacle> list = new ArrayList<>();
        for (ObstacleCreator creator : creators.values()) {
            list.addAll(creator.getInUse());
        }
        return list;
    }

    public List<String> getRegisteredCreatorNames() {
        return new ArrayList<>(creators.keySet());
    }
}
