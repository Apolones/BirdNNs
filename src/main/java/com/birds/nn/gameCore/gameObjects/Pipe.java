package com.birds.nn.gameCore.gameObjects;

import com.birds.nn.utils.Config;

import java.util.concurrent.ThreadLocalRandom;

public class Pipe extends Block {
    private final double speed;
    private final double holeSize;
    private final double width;

    public Pipe(Config config) {
        super(config.game.gameWidth, config.game.gameHeight / 2);
        speed = config.game.pipeConfig.speed;
        holeSize = config.game.pipeConfig.holeSize;
        width = config.game.pipeConfig.width;
    }

    @Override
    public void updateState() {
        setX(getX() - speed);
    }

    public void setRandomPosition(double x, double y) {
        setX(x);
        setY(ThreadLocalRandom.current().nextDouble(holeSize, y - holeSize));
    }

    public boolean isOutOfBounds() {
        return getX() + width <= 0;
    }

    public double getHoleSize() {
        return holeSize;
    }

    public double getWidth() {
        return width;
    }
}
