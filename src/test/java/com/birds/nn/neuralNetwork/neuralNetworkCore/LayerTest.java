package com.birds.nn.neuralNetwork.neuralNetworkCore;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LayerTest {

    @Test
    public void layerInitialization() {
        int numInputs = 3;
        int numNeuron = 3;

        Layer layer = new Layer(numNeuron, numInputs);

        assertEquals(numNeuron, layer.getNeurons().length);
        for (Neuron neuron : layer.getNeurons()) {
            assertEquals(numInputs, neuron.getWeights().length);
        }
    }

    @Test
    public void activateWithCorrectInputs() {
        int numInputs = 3;
        int numNeuron = 3;
        Layer layer = new Layer(numNeuron, numInputs);
        double[] inputs = {0.1, -0.2, 0.3};

        double[] outputs = layer.activate(inputs);

        for (double output : outputs) {
            assertTrue(output >= -1 && output <= 1);
        }
    }

    @Test
    public void activateWithLessInputsLengthLess() {
        int numInputs = 3;
        int numNeuron = 3;
        Layer layer = new Layer(numNeuron, numInputs);
        double[] inputs = {0.1, -0.2};

        double[] outputs = layer.activate(inputs);

        for (double output : outputs) {
            assertTrue(output >= -1 && output <= 1);
        }
    }

    @Test
    public void activateWithMoreInputsLength() {
        int numInputs = 3;
        int numNeuron = 3;
        Layer layer = new Layer(numNeuron, numInputs);
        double[] inputs = {0.1, -0.2, 0.3, -0.4};

        double[] outputs = layer.activate(inputs);

        for (double output : outputs) {
            assertTrue(output >= -1 && output <= 1);
        }
    }

    @Test
    public void activateWithOutOfRangeInputs() {
        int numInputs = 3;
        int numNeuron = 3;
        Layer layer = new Layer(numNeuron, numInputs);
        double[] inputs = {2, -3, 0.1};

        double[] outputs = layer.activate(inputs);

        for (double output : outputs) {
            assertTrue(output >= -1 && output <= 1);
        }
    }
}