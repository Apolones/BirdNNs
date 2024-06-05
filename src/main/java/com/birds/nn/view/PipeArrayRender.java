package com.birds.nn.view;

import com.birds.nn.model.Pipe;
import com.birds.nn.model.PipeArray;
import javafx.scene.canvas.GraphicsContext;

public class PipeArrayRender {
    public static void render(GraphicsContext context, PipeArray pipeArray) {
        for (Pipe pipe : pipeArray.getPipeArray()) {
            PipeRender.render(context, pipe);
        }
    }
}
