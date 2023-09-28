package com.birds.nns;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Pipe extends Block{
    private double speed=2.0;
    public double holeSize=50.0;
    private double width=25.0;

//    private Bird bird;


    public Pipe(double x, double y/*, Bird bird*/) {
        super(x, y);
//        this.bird= bird;
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
//        if (x<(bird.x+bird.radius))
//            if (bird.y>(y+holeSize) || bird.y<(y-holeSize)) bird.GameOver();
    }


}
