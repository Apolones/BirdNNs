package com.birds.nn.model;

import com.birds.nn.view.MainApplication;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

public class Pipe extends Block{
    private static final double speed;
    private static final double holeSize;
    private static final double width;
    private static final double imageTopHeight;
    private static final double imageTopWight;
    private static final Image img;

    static {
        speed = MainApplication.getConfig().game.pipeConfig.speed;
        holeSize = MainApplication.getConfig().game.pipeConfig.holeSize;
        width = MainApplication.getConfig().game.pipeConfig.width;
        imageTopHeight = MainApplication.getConfig().game.pipeConfig.imageTopHeight;
        imageTopWight = MainApplication.getConfig().game.pipeConfig.imageTopWight;
        img = new Image(MainApplication.getConfig().resources.pipeImage);
    }

    public Pipe(double x, double y){
        super(x,y, img);
    }

    @Override
    public void Render(GraphicsContext context){
        context.setFill(Color.GREEN);
        if(!hideHitbox) {
            context.fillRect(x, 0, width, y - holeSize);
            context.fillRect(x, y + holeSize, width, context.getCanvas().getHeight());
        }
        else {
            context.drawImage(image, x, 0, width, y - holeSize);
            context.drawImage(image, x - imageTopWight, y - holeSize - imageTopHeight, width + (2* imageTopWight), imageTopHeight);

            context.drawImage(image, x, y + holeSize, width, context.getCanvas().getHeight());
            context.drawImage(image, x - imageTopWight, y + holeSize, width + (2* imageTopWight), imageTopHeight);
        }
    }
    @Override
    public void UpdateState(){
        x-=speed;
    }
    public static double getSpeed(){return speed;}
    public static double getHoleSize(){return holeSize;}
    public static double getWidth(){return width;}

}
