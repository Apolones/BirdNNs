package com.birds.nn.model;

import com.birds.nn.view.MainApplication;

import java.util.ArrayList;

public class Bird extends Block {
    private static final int startPositionX;
    private static final int startPositionY;
    private static final double acceleration;
    private static final double maxSpeed;
    private static final double tapSpeed;
    private static final int radius;

    static {
        startPositionX = MainApplication.getConfig().game.birdConfig.startPositionX;
        startPositionY = MainApplication.getConfig().game.birdConfig.startPositionY;
        acceleration = MainApplication.getConfig().game.birdConfig.acceleration;
        maxSpeed = MainApplication.getConfig().game.birdConfig.maxSpeed;
        tapSpeed = MainApplication.getConfig().game.birdConfig.tapSpeed;
        radius = MainApplication.getConfig().game.birdConfig.radius;
    }

    private double speed;
    private double headAngle;
    private long score;
    private boolean isDead;

    public Bird() {
        super(startPositionX, startPositionY);
        speed = 0;
        headAngle = -15;
        score = 0;
        isDead = false;
    }

    public void updateState(ArrayList<Pipe> pipeBlock, double maxY) {
        if (isDead) return;
        Pipe pipe = NearestPipe(pipeBlock);
        if (pipe.x < x + radius)
            if (pipe.x + Pipe.getWidth() > x - radius)
                if (y + radius > pipe.y + Pipe.getHoleSize() || y - radius < pipe.y - Pipe.getHoleSize()) {
                    dead();
                    return;
                }
        if (speed + acceleration < maxSpeed) speed += acceleration;
        else speed = maxSpeed;
        y += speed;
        score++;
        updateHeadAngle();
        if (y <= 0 || y >= maxY) dead();
    }

    protected Pipe NearestPipe(ArrayList<Pipe> pipeBlock) {
        Pipe minPipe = null;

        for (Pipe pipe : pipeBlock) {
            if (pipe.getX() + Pipe.getWidth() > x - radius) {
                if (minPipe == null || pipe.getX() < minPipe.getX()) {
                    minPipe = pipe;
                }
            }
        }
        return minPipe;
    }

    public void Tap() {
        this.speed = -tapSpeed;
    }

    private void updateHeadAngle() {
        if (speed > maxSpeed / 2 && headAngle < 75) headAngle += 3;
        if (speed < maxSpeed / 2 && headAngle > -15) headAngle -= 3;
    }

    public void dead() {
        isDead = true;
    }

    public void alive() {
        score = 0;
        isDead = false;
    }

    public static int getRadius() {
        return radius;
    }

    public static double getMaxSpeed() {
        return maxSpeed;
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

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
