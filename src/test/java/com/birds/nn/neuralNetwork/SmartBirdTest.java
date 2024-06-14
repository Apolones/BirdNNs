package com.birds.nn.neuralNetwork;

import com.birds.nn.gameCore.gameObjects.Pipe;
import com.birds.nn.gameCore.gameObjects.PipeFactory;
import com.birds.nn.neuralNetwork.neuralNetworkCore.NeuralNetwork;
import com.birds.nn.utils.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SmartBirdTest {
    @Mock
    private Config config;

    @Mock
    private Config.GameConfig game;

    @Mock
    private Config.GameConfig.BirdConfig birdConfig;

    @Mock
    private NeuralNetwork neuralNetwork;

    @Mock
    private PipeFactory pipeFactory;

    @Mock
    private Pipe pipe;

    private SmartBird smartBird;

    @BeforeEach
    void setUp() {
        config.game = game;
        game.birdConfig = birdConfig;

        birdConfig.startPositionX = 0;
        birdConfig.startPositionY = 0;
        birdConfig.gravity = 0.98;
        birdConfig.maxSpeed = 15.0;
        birdConfig.tapSpeed = 10.0;
        birdConfig.radius = 10.0;

        game.gameHeight = 400;
        game.gameWidth = 400;

        ArrayList<Pipe> pipes = new ArrayList<>();
        pipes.add(pipe);
        when(pipe.getX()).thenReturn(400.0);
        when(pipe.getY()).thenReturn(200.0);
        when(pipe.getWidth()).thenReturn(10.0);
        when(pipe.getHoleSize()).thenReturn(10.0);
        when(pipeFactory.getPipes()).thenReturn(pipes);

        smartBird = spy(new SmartBird(pipeFactory, config, neuralNetwork));
    }

    @Test
    void updateStateNeuralNetworkReturn1() {
        when(neuralNetwork.activate(any())).thenReturn(new double[]{1});

        smartBird.updateState();

        verify(smartBird, times(1)).tap();
    }

    @Test
    void updateStateNeuralNetworkReturn0() {
        when(neuralNetwork.activate(any())).thenReturn(new double[]{0});

        smartBird.updateState();

        verify(smartBird, never()).tap();
    }
}