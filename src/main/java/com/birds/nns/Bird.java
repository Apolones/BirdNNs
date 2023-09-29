package com.birds.nns;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Bird extends Block{
    private double acceleration;
    private double speed;
    private double maxSpeed;
    private double headAngle;
    private double tapAcceleration;
    public long score;
    public static int radius;
    private boolean isDead;

    public Bird(double x, double y) {
        super(x, y);
        acceleration = 0.3;
        speed = 0;
        maxSpeed=10;
        headAngle = 0;
        tapAcceleration = 10;
        radius=10;
        isDead = false;
        score = 0;
    }

    public void Tap (){
        this.speed = -8;
    }

    @Override
    void Render(GraphicsContext context){
        if (y<=0 || y>=context.getCanvas().getHeight()) GameOver();
        if (isDead) {
//            context.setFill(Color.PURPLE);
//            context.setFont(Font.font(50));
//            context.fillText("You LOSE " + score, 100,100);
            return;
        }

        context.setFill(Color.YELLOW);
        context.fillOval(x-radius, y-radius, radius*2, radius*2);

        context.setFont(Font.font(25));
        context.fillText(String.valueOf(score), 60,30);
    }

    void UpdateState(Pipe pipe){
        if (pipe.x<(this.x+this.radius))
            if (this.y>(pipe.y+pipe.getHole()) || this.y<(pipe.y-pipe.getHole())) {
                GameOver();
                return;
            }
        if(speed<maxSpeed) speed += acceleration;
        y += speed;

        if (!isDead) score++;
    }

    void GameOver(){
        isDead=true;
    }

    public double getSpeed() {
        return speed;
    }

    public double getAcceleration() {
        return acceleration;
    }


    public boolean isDead() {
        return isDead;
    }

}
