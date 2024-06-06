package com.birds.nn.model;

import com.birds.nn.utils.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class PipeFactory {
    private final ArrayList<Pipe> pipes;
    private final double count;

    @Autowired
    Config config;

    @Autowired
    public PipeFactory(Config config) {
        this.pipes = new ArrayList<>();
        count = config.game.pipeConfig.count;
    }

    public void updateState(double maxX, double maxY) {
        generatePipes(maxX, maxY);
        for (Pipe pipe : pipes) {
            pipe.updateState();
        }
        removePassedPipe();
    }

    private void removePassedPipe() {
        if (!pipes.isEmpty()) {
            pipes.removeIf(Pipe::isOutOfScreen);
        }
    }

    private void generatePipes(double maxX, double maxY) {
        if (!pipes.isEmpty()) {
            Pipe pipeLast = pipes.get(pipes.size() - 1);
            if (maxX - pipeLast.getX() < (maxX / count)) return;
        }
        double y = ThreadLocalRandom.current().nextDouble(config.game.pipeConfig.holeSize + 10,  maxY - config.game.pipeConfig.holeSize - 10);
        Pipe pipe = new Pipe(config);
        pipe.setInitialPosition(maxX, y);
        pipes.add(pipe);
    }

    public void clearPipeArray() {
        pipes.clear();
    }

    public ArrayList<Pipe> getPipes() {
        return pipes;
    }
}
