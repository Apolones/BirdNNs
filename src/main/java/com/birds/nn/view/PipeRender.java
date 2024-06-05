package com.birds.nn.view;

import com.birds.nn.model.Pipe;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class PipeRender {

    private static final double imageTopHeight;
    private static final double imageTopWight;
    private static final Image image;

    static {
        imageTopHeight = MainApplication.getConfig().game.pipeConfig.imageTopHeight;
        imageTopWight = MainApplication.getConfig().game.pipeConfig.imageTopWight;
        image = new Image(MainApplication.getConfig().resources.pipeImage);
    }

    public static void render(GraphicsContext context, Pipe pipe) {
        double x = pipe.getX();
        double y = pipe.getY();
        double width = Pipe.getWidth();
        double holeSize = Pipe.getHoleSize();

        context.setFill(Color.GREEN);

        if (Pipe.showHitbox()) {
            context.fillRect(x, 0, width, y - holeSize);
            context.fillRect(x, y + holeSize, width, context.getCanvas().getHeight());
        } else {
            context.drawImage(image, x, 0, width, y - holeSize);
            context.drawImage(image, x - imageTopWight, y - holeSize - imageTopHeight, width + (2 * imageTopWight), imageTopHeight);

            context.drawImage(image, x, y + holeSize, width, context.getCanvas().getHeight());
            context.drawImage(image, x - imageTopWight, y + holeSize, width + (2 * imageTopWight), imageTopHeight);
        }
    }
}
