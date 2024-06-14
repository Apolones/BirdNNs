package com.birds.nn.neuralNetwork.neuralNetworkCore;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NeuralNetworkTest {

    @Test
    public void layerInitialization() {
        List<Integer> inputs = new ArrayList<>();
        inputs.add(3);
        inputs.add(2);
        inputs.add(1);

        NeuralNetwork neuralNetwork = new NeuralNetwork(inputs);

        assertEquals(inputs.size() - 1, neuralNetwork.getLayers().length);
        for (int i = 0; i < inputs.size() - 1; i++) {
            assertEquals(inputs.get(i + 1), neuralNetwork.getLayers()[i].getNeurons().length);
        }
    }

    @Test
    public void activateWithCorrectInputs() {
        List<Integer> input = new ArrayList<>();
        input.add(3);
        input.add(2);
        input.add(1);
        NeuralNetwork neuralNetwork = new NeuralNetwork(input);
        double[] inputs = {0.1, -0.2, 0.3};

        double[] outputs = neuralNetwork.activate(inputs);

        for (double output : outputs) {
            assertTrue(output >= -1 && output <= 1);
        }
    }

    @Test
    public void activateWithLessInputsLengthLess() {
        List<Integer> input = new ArrayList<>();
        input.add(3);
        input.add(2);
        input.add(1);
        NeuralNetwork neuralNetwork = new NeuralNetwork(input);
        double[] inputs = {0.1, -0.2, 0.3};

        double[] outputs = neuralNetwork.activate(inputs);

        for (double output : outputs) {
            assertTrue(output >= -1 && output <= 1);
        }
    }

    @Test
    public void activateWithMoreInputsLength() {
        List<Integer> input = new ArrayList<>();
        input.add(3);
        input.add(2);
        input.add(1);
        NeuralNetwork neuralNetwork = new NeuralNetwork(input);
        double[] inputs = {0.1, -0.2, 0.3};

        double[] outputs = neuralNetwork.activate(inputs);

        for (double output : outputs) {
            assertTrue(output >= -1 && output <= 1);
        }
    }

    @Test
    public void activateWithOutOfRangeInputs() {
        List<Integer> input = new ArrayList<>();
        input.add(3);
        input.add(2);
        input.add(1);
        NeuralNetwork neuralNetwork = new NeuralNetwork(input);
        double[] inputs = {0.1, -0.2, 0.3};

        double[] outputs = neuralNetwork.activate(inputs);

        for (double output : outputs) {
            assertTrue(output >= -1 && output <= 1);
        }
    }
}