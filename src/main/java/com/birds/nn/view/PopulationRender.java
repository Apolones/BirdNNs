package com.birds.nn.view;

import com.birds.nn.model.Bird;
import com.birds.nn.model.Population;
import javafx.scene.canvas.GraphicsContext;

public class PopulationRender {
    public static void render(GraphicsContext context, Population population) {
        for (Bird bird : population.getSmartBirds()) {
            BirdRender.render(context, bird);
        }
    }
}
