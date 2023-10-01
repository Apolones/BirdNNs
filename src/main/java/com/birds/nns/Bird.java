package com.birds.nns;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

public class Bird extends Block{
    private double acceleration;
    private double speed;
    private double maxSpeed;
    private double headAngle;
    private double tapSpeed;
    public long score;
    public static int radius;
    public static int scaleImage;
    public static boolean hideHitbox=true;
    private boolean isDead;

    public Bird(double x, double y, Image image) {
        super(x, y,image);
        acceleration = 0.1;
        speed = 0;
        maxSpeed=5.8;
        headAngle = -15;
        tapSpeed = 3.5;
        radius=15;
        scaleImage=3;
        isDead = false;
        score = 0;
    }

    public void Tap (){
        this.speed = -tapSpeed;
    }

//    @Override
    void Render(GraphicsContext context){
        updateHeadAngle();
        if (y<=0 || y>=context.getCanvas().getHeight()) birdDead();
        if (isDead) {
            return;
        }

        if(!hideHitbox){
            context.setFill(Color.YELLOW);
            context.fillRect(x-radius,y-radius,2*radius,2*radius);
        }
        else {
            drawRotatedImage(context,image,headAngle,x-(scaleImage*radius/2) ,y-(scaleImage*radius/2), radius*scaleImage, radius*scaleImage );
        }
    }

    void UpdateState(Pipe pipe){
        if (pipe.x<(x+radius))
            if (pipe.x+pipe.getWidth()>(x-radius))
                if ((y+radius)>(pipe.y+pipe.getHole()) || (y-radius)<(pipe.y-pipe.getHole())) {
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
        if(speed>maxSpeed/2 && headAngle<75)headAngle+=3;
        if(speed<maxSpeed/2 && headAngle>-15)headAngle-=3;
    }

}
