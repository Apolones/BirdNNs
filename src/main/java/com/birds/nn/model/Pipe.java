package com.birds.nn.model;

import com.birds.nn.view.MainApplication;

public class Pipe extends Block {
    private static final double speed;
    private static final double holeSize;
    private static final double width;

    static {
        speed = MainApplication.getConfig().game.pipeConfig.speed;
        holeSize = MainApplication.getConfig().game.pipeConfig.holeSize;
        width = MainApplication.getConfig().game.pipeConfig.width;
    }

    public Pipe(double x, double y) {
        super(x, y);
    }

    @Override
    public void updateState() {
        x -= speed;
    }

    public static double getHoleSize() {
        return holeSize;
    }

    public static double getWidth() {
        return width;
    }
}
