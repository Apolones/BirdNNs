package com.birds.nn.neuralnetwork;

import java.util.List;

public class NeuralNetwork {
    private Layer[] layers;

    public NeuralNetwork(List<Integer> inputs) {
        layers = new Layer[inputs.size() - 1];
        for (int i = 0; i < inputs.size() - 1; i++)
            layers[i] = new Layer(inputs.get(i+1), inputs.get(i));
    }

    public double[] feedForward(double[] inputs) {
        double[] outputs = inputs;
        for (Layer layer : layers) {
            outputs = layer.activate(outputs);
        }
        return outputs;
    }

    public Layer[] getLayers() {
        return layers;
    }
}
