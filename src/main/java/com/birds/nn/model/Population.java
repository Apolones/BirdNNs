package com.birds.nn.model;

import com.birds.nn.model.neuralnetwork.Layer;
import com.birds.nn.model.neuralnetwork.NeuralNetwork;
import com.birds.nn.model.neuralnetwork.Neuron;
import com.birds.nn.view.MainApplication;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Population {
    private final int populationSize;
    private final List<Integer> inputs;

    private List<SmartBird> smartBirds;
    private SmartBird bestBird;
    private long bestScore = 0;

    public Population(int populationSize, int numInputs, List<Integer> numHidden, int numOutputs) {
        this.populationSize = populationSize;
        numHidden.add(numOutputs);
        numHidden.add(0, numInputs);
        this.inputs = numHidden;
        smartBirds = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            smartBirds.add(new SmartBird(new NeuralNetwork(inputs)));
        }
    }

    public void updateState(PipeArray pipeArray, double maxX, double maxY) {
        for (SmartBird smartBird : smartBirds) {
            if (smartBird.isDead()) continue;
            smartBird.updateState(pipeArray.getPipeArray(), maxX, maxY);
        }
    }

    public SmartBird crossover(SmartBird parent1, SmartBird parent2) {
        SmartBird child = new SmartBird(new NeuralNetwork(inputs));
        for (int i = 0; i < parent1.getNeuralNetwork().getLayers().length; i++) {
            Layer parent1Layer = parent1.getNeuralNetwork().getLayers()[i];
            Layer parent2Layer = parent2.getNeuralNetwork().getLayers()[i];
            Layer childLayer = child.getNeuralNetwork().getLayers()[i];
            for (int j = 0; j < parent1Layer.getNeurons().length; j++) {
                Neuron parent1Neuron = parent1Layer.getNeurons()[j];
                Neuron parent2Neuron = parent2Layer.getNeurons()[j];
                Neuron childNeuron = childLayer.getNeurons()[j];
                boolean rnd = ThreadLocalRandom.current().nextDouble() > 0.5;
                for (int k = 0; k < parent1Neuron.getWeights().length; k++) {
                    if (rnd) {
                        childNeuron.getWeights()[k] = parent1Neuron.getWeights()[k];
                    } else {
                        childNeuron.getWeights()[k] = parent2Neuron.getWeights()[k];
                    }
                }
            }
        }
        return child;
    }

    public void mutate(SmartBird smartBird, double mutationRate) {
        for (Layer layer : smartBird.getNeuralNetwork().getLayers()) {
            for (Neuron neuron : layer.getNeurons()) {
                for (int i = 0; i < neuron.getWeights().length; i++) {
                    if (ThreadLocalRandom.current().nextDouble() < mutationRate) {
                        neuron.getWeights()[i] = ThreadLocalRandom.current().nextDouble(-1, 1);
                    }
                }
            }
        }
    }

    public void evolve(double mutationRate, int eliteCount) {

        List<SmartBird> newGeneration = new ArrayList<>();
        smartBirds.sort(Comparator.comparing(Bird::getScore).reversed());

        if (bestScore < smartBirds.get(0).getScore()) {
            bestScore = smartBirds.get(0).getScore();
            bestBird = smartBirds.get(0);
        }

        bestBird.alive();
        newGeneration.add(bestBird);

        for (int i = 0; i < eliteCount; i++) {
            if (smartBirds.get(i) != bestBird) {
                smartBirds.get(i).alive();
                newGeneration.add(smartBirds.get(i));
            }
        }


        Random rand = new Random();
        while (newGeneration.size() < populationSize) {
            SmartBird parent1 = smartBirds.get(rand.nextInt(eliteCount));
            SmartBird parent2 = smartBirds.get(rand.nextInt(eliteCount));
            SmartBird child = crossover(parent1, parent2);
            mutate(child, mutationRate);
            newGeneration.add(child);
        }
        smartBirds = newGeneration;
    }

    public Boolean isNextGen() {
        for (SmartBird smartBird : smartBirds) {
            if (!smartBird.isDead()) return false;
        }
        evolve(MainApplication.getConfig().neuralNetwork.mutationRate, MainApplication.getConfig().neuralNetwork.eliteCounter);
        return true;
    }

    public List<SmartBird> getSmartBirds() {
        return smartBirds;
    }
}
