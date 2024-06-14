package com.birds.nn.gameCore.gameObjects;

import com.birds.nn.neuralNetwork.Population;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UITest {

    @Mock
    private Population population;

    private UI ui;

    @BeforeEach
    void setUp() {
        ui = new UI(population);
    }

    @Test
    void initialState() {
        assertEquals(0, ui.getMaxScore());
        assertEquals(0, ui.getScore());
        assertEquals(0, ui.getGeneration());
        assertEquals(0, ui.getNumBirds());
    }

    @Test
    void updateState() {
        long initialScore = ui.getScore();
        when(population.countLiveBirds()).thenReturn(1);
        when(population.getGeneration()).thenReturn(1);
        when(population.getBestScore()).thenReturn(5L);

        ui.updateState();

        assertEquals(1, ui.getGeneration());
        assertEquals(1, ui.getNumBirds());
        assertEquals(5, ui.getMaxScore());
        assertEquals(initialScore + 1, ui.getScore());
    }

    @Test
    void updateStateUpdatesMaxScore() {
        when(population.getBestScore()).thenReturn(0L);

        ui.updateState();

        assertEquals(1, ui.getMaxScore());
    }

}