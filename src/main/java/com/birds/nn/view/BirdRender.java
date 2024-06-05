package com.birds.nn.view;

import com.birds.nn.model.Bird;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class BirdRender {
    private static final int scaleImage;
    private static final Image image;

    static {
        scaleImage = MainApplication.getConfig().game.birdConfig.scaleImage;
        image = new Image(MainApplication.getConfig().resources.birdImage);
    }

    public static void render(GraphicsContext context, Bird bird) {
        if (bird.isDead()) return;

        double x = bird.getX();
        double y = bird.getY();
        double headAngle = bird.getHeadAngle();
        double radius = Bird.getRadius();

        if (Bird.showHitbox()) {
            context.setFill(Color.YELLOW);
            context.fillRect(x - radius, y - radius, 2 * radius, 2 * radius);
            context.strokeRect(x - radius, y - radius, 2 * radius, 2 * radius);
        } else {
            drawRotatedImage(context, headAngle, x - (scaleImage * radius / 2d), y - (scaleImage * radius / 2d), radius * scaleImage, radius * scaleImage);
        }
    }

    private static void drawRotatedImage(GraphicsContext context,
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


}
