package com.birds.nn.neuralNetwork.neuralNetworkCore;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Neuron {
    private static final Logger logger = Logger.getLogger(Neuron.class.getName());

    private final double[] weights;

    public Neuron(int numInputs) {
        weights = new double[numInputs];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = ThreadLocalRandom.current().nextDouble(-1, 1);
        }
    }

    public double activate(double[] inputs) {
        double[] correctedInputs = correctInputs(inputs);

        double sum = 0;
        for (int i = 0; i < correctedInputs.length; i++) {
            sum += correctedInputs[i] * weights[i];
        }

        return activationFunction(sum);
    }

    public double activationFunction(double x) {
        //Sigmoid
        return 1 / (1 + Math.exp(-x));
    }

    private double[] correctInputs(double[] inputs) {
        if (inputs.length != weights.length) {
            logger.log(Level.SEVERE, "Warning: Wrong amount of inputs (Expected: " + weights.length + " Actual: " + inputs.length + ")");
        }

        double[] correctedInputs = new double[Math.min(inputs.length, weights.length)];
        for (int i = 0; i < correctedInputs.length; i++) {
            correctedInputs[i] = correctInputValue(inputs[i], i);
        }

        return correctedInputs;
    }

    private double correctInputValue(double input, int index) {
        if (input > 1 || input < -1) {
            logger.log(Level.SEVERE, "Warning: Input should be in the range [-1;1] (Input[" + index + "]: " + input + ")");
            return input > 1 ? 1 : -1;
        }
        return input;
    }

    public double[] getWeights() {
        return weights;
    }

}
