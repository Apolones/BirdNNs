package com.birds.nn.gameCore.gameObjects;

import com.birds.nn.utils.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PipeFactory {
    private final ArrayList<Pipe> pipes;
    private final Config config;

    @Autowired
    public PipeFactory(Config config) {
        this.config = config;
        this.pipes = new ArrayList<>();
    }

    public void updateState() {
        generatePipes();
        for (Pipe pipe : pipes) {
            pipe.updateState();
        }
        removeOutOfBoundsPipe();
    }

    private void removeOutOfBoundsPipe() {
        if (!pipes.isEmpty()) {
            pipes.removeIf(Pipe::isOutOfBounds);
        }
    }

    private void generatePipes() {
        if (!pipes.isEmpty()) {
            Pipe pipeLast = pipes.get(pipes.size() - 1);
            if (config.game.windowConfig.gameWidth - pipeLast.getX() < config.game.pipeConfig.distanceBetweenPipe) return;
        }
        Pipe pipe = createPipe();
        pipe.setRandomPosition(config.game.windowConfig.gameWidth, config.game.windowConfig.gameHeight);
        pipes.add(pipe);
    }

    public Pipe createPipe() {
        return new Pipe(config);
    }

    public void clearPipeArray() {
        pipes.clear();
    }

    public ArrayList<Pipe> getPipes() {
        return pipes;
    }
}
