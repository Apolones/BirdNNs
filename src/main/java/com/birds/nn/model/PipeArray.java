package com.birds.nn.model;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class PipeArray {
    private final ArrayList<Pipe> pipeBlock;

    public PipeArray() {
        pipeBlock = new ArrayList<>();
    }

    public void updateState(double maxX, double maxY) {
        generatePipes(maxX, maxY);
        for (Pipe pipe : pipeBlock) {
            pipe.updateState();
        }
        removePassedPipe();
    }

    private void removePassedPipe() {
        if (!pipeBlock.isEmpty()) {
            pipeBlock.removeIf(pipe -> (pipe.getX() + Pipe.getWidth()) <= 0);
        }
    }

    private void generatePipes(double maxX, double maxY) {
        if (!pipeBlock.isEmpty()) {
            Pipe pipeLast = pipeBlock.get(pipeBlock.size() - 1);
            if (pipeLast.getX() > (maxX / 1.7)) return;
        }
        int y = ThreadLocalRandom.current().nextInt(100, (int) maxY - 100);
        Pipe pipe = new Pipe(maxX, y);
        pipeBlock.add(pipe);
    }

    public void clearPipeArray() {
        pipeBlock.clear();
    }

    public ArrayList<Pipe> getPipeArray() {
        return pipeBlock;
    }
}
