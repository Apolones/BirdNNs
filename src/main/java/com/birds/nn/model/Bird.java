package com.birds.nn.model;

import com.birds.nn.utils.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Bird extends Block {
    protected final double acceleration;
    protected final double maxSpeed;
    protected final double tapSpeed;
    protected final double radius;
    protected double speed;
    protected double headAngle;
    protected long score;
    protected boolean isDead;

    @Autowired
    public Bird(Config config){
        super(config.game.birdConfig.startPositionX, config.game.birdConfig.startPositionY);
        speed = 0;
        headAngle = -15;
        score = 0;
        isDead = false;
        acceleration = config.game.birdConfig.acceleration;
        maxSpeed = config.game.birdConfig.maxSpeed;
        tapSpeed = config.game.birdConfig.tapSpeed;
        radius = config.game.birdConfig.radius;
    }

    public void updateState(ArrayList<Pipe> pipeBlock, double maxY) {
        if (isDead) return;
        Pipe pipe = nearestPipe(pipeBlock);
        if (pipe.x < x + radius)
            if (pipe.x + pipe.getWidth() > x - radius)
                if (y + radius > pipe.y + pipe.getHoleSize() || y - radius < pipe.y - pipe.getHoleSize()) {
                    dead();
                    return;
                }
        if (speed + acceleration < maxSpeed) speed += acceleration;
        else speed = maxSpeed;
        y += speed;
        score++;
        updateHeadAngle();
        if (y <= radius || y >= maxY - radius) dead();
    }

    protected Pipe nearestPipe(ArrayList<Pipe> pipeBlock) {
        Pipe minPipe = null;

        for (Pipe pipe : pipeBlock) {
            if (pipe.getX() + pipe.getWidth() > x - radius) {
                if (minPipe == null || pipe.getX() < minPipe.getX()) {
                    minPipe = pipe;
                }
            }
        }
        //if (minPipe == null) return new PipeFactory().getPipes();
        return minPipe;
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
}
