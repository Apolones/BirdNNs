package com.birds.nn.neuralNetwork.neuralNetworkCore;

public class Layer {
    private final Neuron[] neurons;

    public Layer(int numNeurons, int numInputs) {
        neurons = new Neuron[numNeurons];
        for (int i = 0; i < numNeurons; i++) {
            neurons[i] = new Neuron(numInputs);
        }
    }

    public double[] activate(double[] inputs) {
        double[] outputs = new double[neurons.length];
        for (int i = 0; i < neurons.length; i++) {
            outputs[i] = neurons[i].activate(inputs);
        }
        return outputs;
    }

    public Neuron[] getNeurons() {
        return neurons;
    }
}
