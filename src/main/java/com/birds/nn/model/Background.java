package com.birds.nn.model;

import com.birds.nn.utils.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Background extends Block {

    private final double speed;

    @Autowired
    public Background(Config config) {
        super(0, 0);
        speed = config.game.backgroundConfig.speed;
    }

    public void updateState(double width) {
        x -= speed;
        if (x <= -width)
            x = 0;
    }
}
