package com.birds.nns;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Bird extends Block{
    private double acceleration;
    private double speed;
    private double maxSpeed;
    private double headAngle;
    private double tapSpeed;
    public long score;
    public static int radius;
    private boolean isDead;

    public Bird(double x, double y) {
        super(x, y);
        acceleration = 0.3;
        speed = 0;
        maxSpeed=10;
        headAngle = 0;
        tapSpeed = 7;
        radius=10;
        isDead = false;
        score = 0;
    }

    public void Tap (){
        this.speed = -tapSpeed;
    }

//    @Override
    void Render(GraphicsContext context){
        if (y<=0 || y>=context.getCanvas().getHeight()) birdDead();
        if (isDead) {
            return;
        }

        context.setFill(Color.YELLOW);
        context.fillOval(x-radius, y-radius, radius*2, radius*2);

//        context.setFont(Font.font(25));
//        context.fillText(String.valueOf("Score: " + score), 30,30);
    }

    void UpdateState(Pipe pipe){
        if (pipe.x<(x+radius))
            if (pipe.x+pipe.getWidth()>(x-radius))
                if (y>(pipe.y+pipe.getHole()) || y<(pipe.y-pipe.getHole())) {
                    birdDead();
                    return;
                }
        if(speed<maxSpeed) speed += acceleration;
        y += speed;

        if (!isDead) score++;
    }

    void birdDead(){
        isDead=true;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isDead() {
        return isDead;
    }

}
