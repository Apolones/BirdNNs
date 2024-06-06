package com.birds.nn.view;

import com.birds.nn.model.*;
import com.birds.nn.utils.Config;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Render {

    @Autowired
    private Config config;

    public void render(GraphicsContext context, Background background) {
        double x = background.getX();
        double y = background.getY();
        Image image = new Image(config.resources.backgroundImage);

        context.drawImage(image, x, y, context.getCanvas().getWidth(), context.getCanvas().getHeight());
        context.drawImage(image, context.getCanvas().getWidth() + x, y, context.getCanvas().getWidth(), context.getCanvas().getHeight());
    }

    public void render(GraphicsContext context, Bird bird) {
        if (bird.isDead()) return;

        double x = bird.getX();
        double y = bird.getY();
        double headAngle = bird.getHeadAngle();
        double radius = bird.getRadius();
        double scaleImage = config.game.birdConfig.scaleImage;
        Image image = new Image(config.resources.birdImage);

        if (Bird.showHitbox()) {
            context.setFill(Color.YELLOW);
            context.fillRect(x - radius, y - radius, 2 * radius, 2 * radius);
            context.strokeRect(x - radius, y - radius, 2 * radius, 2 * radius);
        } else {
            drawRotatedImage(context, image, headAngle, x - (scaleImage * radius / 2d), y - (scaleImage * radius / 2d), radius * scaleImage, radius * scaleImage);
        }
    }

    public void render(GraphicsContext context, Pipe pipe) {
        double x = pipe.getX();
        double y = pipe.getY();
        double width = pipe.getWidth();
        double holeSize = pipe.getHoleSize();
        double imageTopWight = config.game.pipeConfig.imageTopWight;
        double imageTopHeight = config.game.pipeConfig.imageTopHeight;
        Image image = new Image(config.resources.pipeImage);

        if (Pipe.showHitbox()) {
            context.setFill(Color.GREEN);
            context.fillRect(x, 0, width, y - holeSize);
            context.fillRect(x, y + holeSize, width, context.getCanvas().getHeight());
        } else {
            context.drawImage(image, x, 0, width, y - holeSize);
            context.drawImage(image, x - imageTopWight, y - holeSize - imageTopHeight, width + (2 * imageTopWight), imageTopHeight);

            context.drawImage(image, x, y + holeSize, width, context.getCanvas().getHeight());
            context.drawImage(image, x - imageTopWight, y + holeSize, width + (2 * imageTopWight), imageTopHeight);
        }
    }

    public void render(GraphicsContext context, List<? extends Block> blocks) {
        for (Block block : blocks) {
            if (block instanceof Background) {
                render(context, (Background) block);
            } else if (block instanceof Bird) {
                render(context, (Bird) block);
            } else if (block instanceof Pipe) {
                render(context, (Pipe) block);
            }
        }
    }

    public void render(GraphicsContext context, UI ui) {
        context.setFill(Color.DARKRED);
        context.setFont(Font.font(15));
        context.fillText("Generation: " + ui.getGeneration(), context.getCanvas().getWidth() - 150, 20);
        context.fillText("Maximum score: " + ui.getMaxScore() / 100, context.getCanvas().getWidth() - 150, 40);
        context.fillText("Birds: " + ui.getNumBirds(), 30, 50);

        context.setFont(Font.font(25));
        context.fillText("Score: " + ui.getScore() / 100, 30, 30);
    }

    private static void drawRotatedImage(GraphicsContext context, Image image,
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
