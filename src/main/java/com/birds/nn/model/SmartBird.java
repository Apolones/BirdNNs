package com.birds.nn.model;

import com.birds.nn.model.neuralnetwork.NeuralNetwork;

import java.util.ArrayList;

public class SmartBird extends Bird {

    private final NeuralNetwork neuralNetwork;

    public SmartBird(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

    public void updateState(ArrayList<Pipe> pipeBlock, double maxX, double maxY) {
        super.updateState(pipeBlock, maxY);
        if (isJump(pipeBlock, maxX, maxY)) super.Tap();
    }

    public boolean isJump(ArrayList<Pipe> pipeBlock, double width, double height) {
        Pipe pipe = NearestPipe(pipeBlock);
        return neuralNetwork.activate(new double[]{
                (pipe.getX() - x + Bird.getRadius())                                      //distance to hole on X (normalized)
                        / width,
                (y - Bird.getRadius() - (pipe.getY() + Pipe.getHoleSize()))       //distance to hole on y bottom (normalized)
                        / height,
                (y + Bird.getRadius() - (pipe.getY() + Pipe.getHoleSize()))           //distance to hole on y upper (normalized)
                        / height,
                getSpeed()                                                                   //bird speed (normalized)
                        / Bird.getMaxSpeed()
        })[0] > 0.5;
    }

    public NeuralNetwork getNeuralNetwork() {
        return neuralNetwork;
    }
}
