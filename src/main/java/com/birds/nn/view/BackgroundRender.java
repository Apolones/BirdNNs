package com.birds.nn.view;

import com.birds.nn.model.Background;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BackgroundRender {
    private static final Image image;

    static {
        image = new Image(MainApplication.getConfig().resources.backgroundImage);
    }

    public static void render(GraphicsContext context, Background background) {
        double x = background.getX();
        double y = background.getY();

        context.drawImage(image, x, y, context.getCanvas().getWidth(), context.getCanvas().getHeight());
        context.drawImage(image, context.getCanvas().getWidth() + x, y, context.getCanvas().getWidth(), context.getCanvas().getHeight());
    }
}
