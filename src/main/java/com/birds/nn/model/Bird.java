package com.birds.nn.model;

import com.birds.nn.view.MainApplication;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;

public class Bird extends Block {
    private static final int startPositionX;
    private static final int startPositionY;
    private static final double acceleration;
    private static final double maxSpeed;
    private static final double tapSpeed;
    private static final int radius;
    private static final int scaleImage;
    private static final Image img;

    static {
        startPositionX = MainApplication.getConfig().game.birdConfig.startPositionX;
        startPositionY = MainApplication.getConfig().game.birdConfig.startPositionY;
        acceleration = MainApplication.getConfig().game.birdConfig.acceleration;
        maxSpeed = MainApplication.getConfig().game.birdConfig.maxSpeed;
        tapSpeed = MainApplication.getConfig().game.birdConfig.tapSpeed;
        radius = MainApplication.getConfig().game.birdConfig.radius;
        scaleImage = MainApplication.getConfig().game.birdConfig.scaleImage;
        img = new Image(MainApplication.getConfig().resources.birdImage);
    }

    private double speed;
    private double headAngle;
    private long score;
    private boolean isDead;

    public Bird() {
        super(startPositionX, startPositionY, img);
        speed = 0;
        headAngle = -15;
        score = 0;
        isDead = false;
    }

    public void Tap() {
        this.speed = -tapSpeed;
    }

    public void Render(GraphicsContext context) {
        if (isDead) return;
        updateHeadAngle();
        if (y <= 0 || y >= context.getCanvas().getHeight()) birdDead();


        if (!hideHitbox) {
            context.setFill(Color.YELLOW);
            context.fillRect(x - radius, y - radius, 2 * radius, 2 * radius);
        } else {
            drawRotatedImage(context, image, headAngle, x - (scaleImage * radius / 2d), y - (scaleImage * radius / 2d), radius * scaleImage, radius * scaleImage);
        }
    }

    public void UpdateState(ArrayList<Pipe> pipeBlock) {
        Pipe pipe = NearestPipe(pipeBlock);
        if (isDead) return;
        if (pipe.x < x + radius)
            if (pipe.x + Pipe.getWidth() > x - radius)
                if (y + radius > pipe.y + Pipe.getHoleSize() || y - radius < pipe.y - Pipe.getHoleSize()) {
                    birdDead();
                    return;
                }
        if (speed + acceleration < maxSpeed) speed += acceleration;
        else speed = maxSpeed;
        y += speed;
        score++;
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

    public void birdDead() {
        isDead = true;
    }

    public static void drawRotatedImage(GraphicsContext context, Image image,
                                        double angle, double x, double y, double width,
                                        double height) {
        context.save();
        rotate(context, angle, x + width / 2, y + height / 2);
        context.drawImage(image, x, y, width, height);
        context.restore();
    }

    private static void rotate(GraphicsContext context, double angle, double x,
                               double y) {
        Rotate r = new Rotate(angle, x, y);
        context.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(),
                r.getTx(), r.getTy());
    }

    private void updateHeadAngle() {
        if (speed > maxSpeed / 2 && headAngle < 75) headAngle += 3;
        if (speed < maxSpeed / 2 && headAngle > -15) headAngle -= 3;
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

    public double getSpeed() {
        return speed;
    }

    public boolean isDead() {
        return isDead;
    }

    public long getScore() {
        return score;
    }


}
