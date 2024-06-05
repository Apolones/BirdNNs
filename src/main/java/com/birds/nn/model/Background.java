package com.birds.nn.model;

import com.birds.nn.view.MainApplication;

public class Background extends Block {

    private static final double speed;

    static {
        speed = MainApplication.getConfig().game.backgroundConfig.speed;
    }

    public Background() {
        super(0, 0);
    }

    public void updateState(double width) {
        x -= speed;
        if (x <= -width)
            x = 0;
    }
}
