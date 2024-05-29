package com.birds.nn.model;

import com.birds.nn.view.MainApplication;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Background extends Block {
    private static final Image img;

    static {
        img = new Image(MainApplication.getConfig().resources.backgroundImage);
    }

    private final double width;
    private final double height;

    public Background(double width, double height){
        super(0, 0, img);
        this.width = width;
        this.height = height;
    }

    public void Render(GraphicsContext context) {
        context.drawImage(image, x, y, width, height);
        context.drawImage(image, width + x, y, width, height);
    }

    public void UpdateState(){
        x -= Pipe.getSpeed() / 10;
        if (x <= -width)
            x = 0;
    }
}
