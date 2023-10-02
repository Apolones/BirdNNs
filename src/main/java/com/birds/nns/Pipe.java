package com.birds.nns;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

public class Pipe extends Block{
    private static final double speed = 1.0;
    private static final double holeSize = 55.0;
    private static final double width = 40.0;
    private static final double pipeImageTopHeight = 20;
    private static final double pipeImageTopWight = 3;

    public Pipe(double x, double y, Image Image) {
        super(x, y, Image);
    }

    @Override
    void Render(GraphicsContext context){
        context.setFill(Color.GREEN);
        if(!hideHitbox) {
            context.fillRect(x, 0, width, y - holeSize);
            context.fillRect(x, y + holeSize, width, context.getCanvas().getHeight());
        }
        else {
            context.drawImage(image, x, 0, width, y - holeSize);
            context.drawImage(image, x - pipeImageTopWight, y - holeSize - pipeImageTopHeight, width + (2*pipeImageTopWight), pipeImageTopHeight);

            context.drawImage(image, x, y + holeSize, width, context.getCanvas().getHeight());
            context.drawImage(image, x - pipeImageTopWight, y + holeSize, width + (2*pipeImageTopWight), pipeImageTopHeight);
        }
    }
    @Override
    void UpdateState(){
        x-=speed;
    }
    public static double getSpeed(){return speed;}
    public static double getHoleSize(){return holeSize;}
    public static double getWidth(){return width;}

}
