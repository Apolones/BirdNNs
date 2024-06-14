package com.birds.nn.gameCore.gameObjects;

import com.birds.nn.utils.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Bird extends Block {
    private final double gravity;
    private final double maxSpeed;
    private final double tapSpeed;
    private final double radius;
    private double speed;
    private double headAngle;
    private long score;
    private boolean isDead;

    @Autowired
    public Bird(Config config) {
        super(config.game.birdConfig.startPositionX, config.game.birdConfig.startPositionY);
        speed = 0;
        headAngle = -15;
        score = 0;
        isDead = false;
        gravity = config.game.birdConfig.gravity;
        maxSpeed = config.game.birdConfig.maxSpeed;
        tapSpeed = config.game.birdConfig.tapSpeed;
        radius = config.game.birdConfig.radius;
    }

    public void updateState() {
        if (isDead) return;
        if (speed + gravity < maxSpeed) speed += gravity;
        else speed = maxSpeed;
        setY(getY() + speed);
        score++;
        updateHeadAngle();
    }

    public void tap() {
        this.speed = -tapSpeed;
    }

    private void updateHeadAngle() {
        if (speed > maxSpeed / 2 && headAngle < 75) headAngle += 3;
        if (speed < maxSpeed / 2 && headAngle > -15) headAngle -= 3;
    }

    public void dead() {
        isDead = true;
    }

    public double getRadius() {
        return radius;
    }

    public boolean isDead() {
        return isDead;
    }

    public double getSpeed() {
        return speed;
    }

    public long getScore() {
        return score;
    }

    public double getHeadAngle() {
        return headAngle;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }
}
