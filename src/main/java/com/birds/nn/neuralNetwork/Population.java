package com.birds.nn.neuralNetwork;

import com.birds.nn.gameCore.gameObjects.Bird;
import com.birds.nn.neuralNetwork.neuralNetworkCore.Layer;
import com.birds.nn.neuralNetwork.neuralNetworkCore.NeuralNetwork;
import com.birds.nn.neuralNetwork.neuralNetworkCore.Neuron;
import com.birds.nn.utils.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class Population {
    private final int populationSize;
    private final double mutationRate;
    private final int eliteCounter;
    private int generation;

    private List<SmartBird> smartBirds;
    private NeuralNetwork bestNeuralNetwork;
    private long bestScore;

    private final SmartBirdFactory smartBirdFactory;

    @Autowired
    public Population(Config config, SmartBirdFactory smartBirdFactory) {
        bestScore = 0;
        generation = 0;
        this.smartBirdFactory = smartBirdFactory;
        this.populationSize = config.neuralNetwork.populationSize;
        this.mutationRate = config.neuralNetwork.mutationRate;
        this.eliteCounter = config.neuralNetwork.eliteCounter;
        initializePopulation();
    }

    private void initializePopulation() {
        smartBirds = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            SmartBird smartBird = smartBirdFactory.createSmartBird();
            smartBirds.add(smartBird);
        }
    }


    public void updateState() {
        for (SmartBird smartBird : smartBirds) {
            if (smartBird.isDead()) continue;
            smartBird.updateState();
        }
    }

    private SmartBird crossover(SmartBird parent1, SmartBird parent2) {
        SmartBird child = smartBirdFactory.createSmartBird();
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

    private void mutate(SmartBird smartBird, double mutationRate) {
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

    /**
     * Evolves the population by selecting elite birds, performing crossover and mutation,
     * and generating a new generation of smart birds.
     *
     * @param mutationRate The rate at which mutation should occur during evolution.
     * @param eliteCount   The number of elite birds to retain without modification.
     */
    private void evolve(double mutationRate, int eliteCount) {
        List<SmartBird> newGeneration = new ArrayList<>();
        smartBirds.sort(Comparator.comparing(Bird::getScore).reversed());

        if (bestScore < smartBirds.get(0).getScore()) {
            bestScore = smartBirds.get(0).getScore();
            bestNeuralNetwork = smartBirds.get(0).getNeuralNetwork();
        }

        SmartBird bestBird = smartBirdFactory.createSmartBird(bestNeuralNetwork);
        newGeneration.add(bestBird);

        for (int i = 0; i < eliteCount; i++) {
            if (smartBirds.get(i).getNeuralNetwork() != bestNeuralNetwork) {
                newGeneration.add(smartBirdFactory.createSmartBird(smartBirds.get(i).getNeuralNetwork()));
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

    public int countLiveBirds() {
        int count = 0;
        for (SmartBird smartBird : smartBirds) {
            if (!smartBird.isDead()) count++;
        }
        return count;
    }

    public void nextGeneration() {
        generation++;
        evolve(mutationRate, eliteCounter);
    }

    public List<SmartBird> getSmartBirds() {
        return smartBirds;
    }

    public int getGeneration() {
        return generation;
    }

    public long getBestScore() {
        return bestScore;
    }
}
