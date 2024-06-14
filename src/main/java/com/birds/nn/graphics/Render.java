package com.birds.nn.graphics;

import com.birds.nn.gameCore.gameObjects.*;
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
    private final Config config;
    private final Image imgBackground;
    private final Image imgBird;
    private final Image imgPipe;

    @Autowired
    public Render(Config config) {
        this.config = config;
        imgBackground = new Image(config.resources.backgroundImage);
        imgBird = new Image(config.resources.birdImage);
        imgPipe = new Image(config.resources.pipeImage);
    }

    public void render(GraphicsContext context, Background background) {
        double x = background.getX();
        double y = background.getY();

        context.drawImage(imgBackground, x, y, context.getCanvas().getWidth(), context.getCanvas().getHeight());
        context.drawImage(imgBackground, context.getCanvas().getWidth() + x, y, context.getCanvas().getWidth(), context.getCanvas().getHeight());
    }

    public void render(GraphicsContext context, Bird bird) {
        if (bird.isDead()) return;

        double x = bird.getX();
        double y = bird.getY();
        double headAngle = bird.getHeadAngle();
        double radius = bird.getRadius();
        double scaleImage = config.game.birdConfig.scaleImage;

        if (Bird.showHitbox()) {
            context.setFill(Color.YELLOW);
            context.fillRect(x - radius, y - radius, 2 * radius, 2 * radius);
            context.strokeRect(x - radius, y - radius, 2 * radius, 2 * radius);
        } else {
            drawRotatedImage(context, imgBird, headAngle, x - (scaleImage * radius / 2d), y - (scaleImage * radius / 2d), radius * scaleImage, radius * scaleImage);
        }
    }

    public void render(GraphicsContext context, Pipe pipe) {
        double x = pipe.getX();
        double y = pipe.getY();
        double width = pipe.getWidth();
        double holeSize = pipe.getHoleSize();
        double imageTopWight = config.game.pipeConfig.imageTopWight;
        double imageTopHeight = config.game.pipeConfig.imageTopHeight;

        if (Pipe.showHitbox()) {
            context.setFill(Color.GREEN);
            context.fillRect(x, 0, width, y - holeSize);
            context.fillRect(x, y + holeSize, width, context.getCanvas().getHeight());
        } else {
            context.drawImage(imgPipe, x, 0, width, y - holeSize);
            context.drawImage(imgPipe, x - imageTopWight, y - holeSize - imageTopHeight, width + (2 * imageTopWight), imageTopHeight);

            context.drawImage(imgPipe, x, y + holeSize, width, context.getCanvas().getHeight());
            context.drawImage(imgPipe, x - imageTopWight, y + holeSize, width + (2 * imageTopWight), imageTopHeight);
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
