package com.birds.nn.neuralNetwork.neuralNetworkCore;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NeuronTest {

    @Test
    public void neuronInitialization() {
        int numInputs = 3;

        Neuron neuron = new Neuron(numInputs);

        assertEquals(numInputs, neuron.getWeights().length);
        for (double weight : neuron.getWeights()) {
            assertTrue(weight >= -1 && weight <= 1);
        }
    }

    @Test
    public void activateWithCorrectInputs() {
        int numInputs = 3;
        Neuron neuron = new Neuron(numInputs);
        double[] inputs = {0.1, -0.2, 0.3};

        double output = neuron.activate(inputs);

        assertTrue(output >= -1 && output <= 1);
    }

    @Test
    public void activateWithLessInputsLengthLess() {
        int numInputs = 3;
        Neuron neuron = new Neuron(numInputs);
        double[] inputs = {0.1, -0.2};

        double output = neuron.activate(inputs);

        assertTrue(output >= -1 && output <= 1);
    }

    @Test
    public void activateWithMoreInputsLength() {
        int numInputs = 3;
        Neuron neuron = new Neuron(numInputs);
        double[] inputs = {0.1, -0.2, 0.3, -0.4};

        double output = neuron.activate(inputs);

        assertTrue(output >= -1 && output <= 1);
    }

    @Test
    public void activateWithOutOfRangeInputs() {
        int numInputs = 3;
        Neuron neuron = new Neuron(numInputs);
        double[] inputs = {2, -3, 0.1};

        double result = neuron.activate(inputs);

        assertTrue(result >= 0 && result <= 1);
    }

    @Test
    public void activationFunction() {
        int numInputs = 3;
        Neuron neuron = new Neuron(numInputs);

        double output = neuron.activationFunction(10);

        assertTrue(output >= -1 && output <= 1);
    }

}