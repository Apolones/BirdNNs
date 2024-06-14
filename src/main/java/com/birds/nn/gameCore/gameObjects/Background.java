package com.birds.nn.gameCore.gameObjects;

import com.birds.nn.utils.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Background extends Block {

    private final double speed;
    private final Config config;

    @Autowired
    public Background(Config config) {
        super(0, 0);
        this.config = config;
        speed = config.game.backgroundConfig.speed;
    }

    public void updateState() {
        setX(getX() - speed);
        if (getX() <= -config.game.windowConfig.gameWidth)
            setX(0);
    }
}
