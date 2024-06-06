package com.birds.nn.model;

import com.birds.nn.model.neuralnetwork.NeuralNetwork;
import com.birds.nn.utils.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SmartBird extends Bird {

    private NeuralNetwork neuralNetwork;

    @Autowired
    public SmartBird(Config config) {
        super(config);
    }

    public void updateState(ArrayList<Pipe> pipeBlock, double maxX, double maxY) {
        super.updateState(pipeBlock, maxY);
        if (isJump(pipeBlock, maxX, maxY)) super.tap();
    }

    public boolean isJump(ArrayList<Pipe> pipeBlock, double width, double height) {
        Pipe pipe = nearestPipe(pipeBlock);
        return neuralNetwork.activate(new double[]{
                (pipe.getX() - x + radius)                                      //distance to hole on X (normalized)
                        / width,
                (y - radius - (pipe.getY() + pipe.getHoleSize()))       //distance to hole on y bottom (normalized)
                        / height,
                (y + radius - (pipe.getY() + pipe.getHoleSize()))           //distance to hole on y upper (normalized)
                        / height,
                getSpeed()                                                                   //bird speed (normalized)
                        / maxSpeed
        })[0] > 0.5;
    }

    public NeuralNetwork getNeuralNetwork() {
        return neuralNetwork;
    }

    public void setNeuralNetwork(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

}
