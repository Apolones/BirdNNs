package com.birds.nns;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

public class Pipe extends Block{
    private double speed;
    private double holeSize;
    private double width;
    private double pipeImageTopHeight;
    private double pipeImageTopWight;
    public static boolean hideHitbox=true;

    public Pipe(double x, double y, Image Image) {
        super(x, y, Image);
        speed=1.0;
        holeSize=55.0;
        width=40.0;
        pipeImageTopHeight=20;
        pipeImageTopWight=3;
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

    public double getHole(){
        return holeSize;
    }

    public double getWidth(){
        return width;
    }
}
