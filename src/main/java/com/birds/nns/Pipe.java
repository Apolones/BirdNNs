package com.birds.nns;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Pipe extends Block{
    private double speed=2.0;
    private double holeSize=50.0;
    private double width=20.0;

    public Pipe(double x, double y) {
        super(x, y);
    }

    @Override
    void Render(GraphicsContext context){
        context.setFill(Color.GREEN);
        context.fillRect( x, 0, width, y-holeSize);
        context.fillRect( x, y+holeSize, width, context.getCanvas().getHeight());
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
