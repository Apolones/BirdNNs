package com.birds.nn.neuralnetwork;

import java.util.Random;

public class Neuron {
    private double[] weights;

    public Neuron(int numInputs) {
        Random random = new Random();
        weights = new double[numInputs];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = random.nextDouble() * 2 -1;
        }
    }

    public double activate(double[] inputs) {
        double sum = 0;
        int amount = inputs.length;
        if (inputs.length != weights.length) {
            System.err.println("Warning: Wrong amount of inputs (Expected: " + weights.length + " Actual: " + inputs.length + ")");
            amount = Math.min(inputs.length, weights.length);
        }
        for (int i = 0; i < amount; i++) {
            if (inputs[i] > 1 || inputs[i] < -1){
                System.err.println("Warning: Inputs should be [-1;1] (Input[" + i + "]: " + inputs[i] + ") changed to 1/" + inputs[i]);
                inputs[i] = 1 / inputs[i];
            }
            sum += inputs[i] * weights[i];
        }
        return activationFunction(sum);
    }

    public double activationFunction(double x) {
        //Sigmoid
        return 1 / (1 + Math.exp(-x));
    }

    public double[] getWeights() {
        return weights;
    }

}
