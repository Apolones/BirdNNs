package com.birds.nn.view;

import com.birds.nn.model.UI;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class UIRender {
    public static void render(GraphicsContext context, UI ui) {
        context.setFill(Color.DARKRED);
        context.setFont(Font.font(15));
        context.fillText("Generation: " + ui.getGeneration(), context.getCanvas().getWidth() - 150, 20);
        context.fillText("Maximum score: " + ui.getMaxScore() / 100, context.getCanvas().getWidth() - 150, 40);
        context.fillText("Birds: " + ui.getNumBirds(), 30, 50);

        context.setFont(Font.font(25));
        context.fillText("Score: " + ui.getScore() / 100, 30, 30);
    }
}
