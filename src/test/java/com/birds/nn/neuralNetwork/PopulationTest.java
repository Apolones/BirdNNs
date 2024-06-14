package com.birds.nn.neuralNetwork;

import com.birds.nn.neuralNetwork.neuralNetworkCore.Layer;
import com.birds.nn.neuralNetwork.neuralNetworkCore.NeuralNetwork;
import com.birds.nn.utils.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PopulationTest {

    @Mock
    private Config config;

    @Mock
    private Config.NeuralNetworkConfig nnConfig;

    @Mock
    private SmartBird smartBird;

    @Mock
    private SmartBirdFactory smartBirdFactory;

    @Mock
    private NeuralNetwork neuralNetwork;

    private Population population;

    @BeforeEach
    void setUp() {
        config.neuralNetwork = nnConfig;

        nnConfig.populationSize = 10;
        nnConfig.inputSize = 4;
        nnConfig.hiddenLayers = new ArrayList<>();
        nnConfig.hiddenLayers.add(5);
        nnConfig.outputSize = 1;
        nnConfig.mutationRate = 0.1;
        nnConfig.eliteCounter = 2;

        when(smartBirdFactory.createSmartBird()).thenReturn(smartBird);
        population = new Population(config, smartBirdFactory);
    }

    @Test
    void initializePopulation() {
        assertEquals(10, population.getSmartBirds().size());
    }

    @Test
    void updateState() {
        List<SmartBird> smartBirds = population.getSmartBirds();
        for (SmartBird bird : smartBirds) {
            when(bird.isDead()).thenReturn(false);
        }

        population.updateState();

        verify(smartBird, times(population.getSmartBirds().size())).updateState();
    }

    @Test
    void updateStateAllBirdsDead() {
        List<SmartBird> smartBirds = population.getSmartBirds();
        for (SmartBird bird : smartBirds) {
            when(bird.isDead()).thenReturn(true);
        }

        population.updateState();

        for (SmartBird bird : smartBirds) {
            verify(bird, times(0)).updateState();
        }
    }

    @Test
    void countLiveBirds() {
        when(smartBird.isDead()).thenReturn(false);
        assertEquals(10, population.countLiveBirds());

        when(smartBird.isDead()).thenReturn(true);
        assertEquals(0, population.countLiveBirds());
    }

    @Test
    void nextGeneration() {
        List<SmartBird> smartBirds = new ArrayList<>(population.getSmartBirds());
        for (SmartBird bird : smartBirds) {
            when(bird.getScore()).thenReturn(100L);
            when(bird.getNeuralNetwork()).thenReturn(neuralNetwork);
            when(neuralNetwork.getLayers()).thenReturn(new Layer[]{});
        }

        population.nextGeneration();

        assertEquals(1, population.getGeneration());
        assertNotNull(population.getSmartBirds());
        assertEquals(10, population.getSmartBirds().size());
    }
}