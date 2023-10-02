package com.birds.nns;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

public class Bird extends Block{
    private static final double acceleration = 0.1;
    private static final  double maxSpeed = 5.8;
    private static final  double tapSpeed = 3.5;
    private static final  int radius = 15;
    private static final  int scaleImage = 3;

    private double speed;
    private double headAngle;
    private long score;
    private boolean isDead;

    public Bird(double y, Image image) {
        super(scaleImage*radius, y,image);
        speed = 0;
        headAngle = -15;
        score = 0;
        isDead = false;
    }

    public void Tap (){
        this.speed = -tapSpeed;
    }

//    @Override
    void Render(GraphicsContext context){
        if (isDead) return;
        updateHeadAngle();
        if (y<=0|| y>=context.getCanvas().getHeight()) birdDead();


        if(!hideHitbox){
            context.setFill(Color.YELLOW);
            context.fillRect(x-radius,y-radius,2*radius,2*radius);
        }
        else {
            drawRotatedImage(context,image,headAngle,x-(scaleImage*radius/2d) ,y-(scaleImage*radius/2d), radius*scaleImage, radius*scaleImage );
        }
    }

    void UpdateState(Pipe pipe){
        if (isDead) return;
        if (pipe.x<(x+radius))
            if (pipe.x+Pipe.getWidth()>(x-radius))
                if ((y+radius)>(pipe.y+Pipe.getHoleSize()) || (y-radius)<(pipe.y-Pipe.getHoleSize())) {
                    birdDead();
                    return;
                }
        if(speed<maxSpeed) speed += acceleration;
        y += speed;
        score++;
    }

    void birdDead(){
        isDead=true;
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

    public static int getRadius(){return radius;}
    public static double getMaxSpeed(){return maxSpeed;}
    public double getSpeed() {return speed;}
    public boolean isDead() {return isDead;}
    public long getScore(){return score;}

}
