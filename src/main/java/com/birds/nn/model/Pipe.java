package com.birds.nn.model;

import com.birds.nn.utils.Config;

public class Pipe extends Block {
    private final double speed;
    private final double holeSize;
    private final double width;

    public Pipe(Config config) {
        super(0, 0);
        speed = config.game.pipeConfig.speed;
        holeSize = config.game.pipeConfig.holeSize;
        width = config.game.pipeConfig.width;
    }

    public void setInitialPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void updateState() {
        x -= speed;
    }

    public boolean isOutOfScreen(){
       return x + width <= 0;
    }

    public double getHoleSize() {
        return holeSize;
    }

    public double getWidth() {
        return width;
    }
}
