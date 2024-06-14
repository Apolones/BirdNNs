package com.birds.nn.neuralNetwork;

import com.birds.nn.gameCore.gameObjects.PipeFactory;
import com.birds.nn.neuralNetwork.neuralNetworkCore.NeuralNetwork;
import com.birds.nn.utils.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SmartBirdFactory {
    private final Config config;
    private final PipeFactory pipeFactory;

    @Autowired
    public SmartBirdFactory(Config config, PipeFactory pipeFactory) {
        this.config = config;
        this.pipeFactory = pipeFactory;
    }

    public SmartBird createSmartBird() {
        ArrayList<Integer> inputs = new ArrayList<>(config.neuralNetwork.hiddenLayers);
        inputs.add(config.neuralNetwork.outputSize);
        inputs.add(0, config.neuralNetwork.inputSize);
        return new SmartBird(pipeFactory, config, new NeuralNetwork(inputs));
    }

    public SmartBird createSmartBird(NeuralNetwork neuralNetwork) {
        return new SmartBird(pipeFactory, config, neuralNetwork);
    }
}