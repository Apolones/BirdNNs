package com.birds.nn.gameCore.gameLogic;

import com.birds.nn.gameCore.gameObjects.Pipe;
import com.birds.nn.gameCore.gameObjects.PipeFactory;
import com.birds.nn.gameCore.gameObjects.UI;
import com.birds.nn.neuralNetwork.Population;
import com.birds.nn.neuralNetwork.SmartBird;
import com.birds.nn.utils.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    private PipeFactory pipeFactory;

    @Mock
    private Population population;

    @Mock
    private UI ui;

    @Mock
    private Config config;

    @Mock
    private Config.GameConfig game;

    @InjectMocks
    private GameService gameService;

    @BeforeEach
    public void setUp() {
        config.game = game;
        game.gameHeight = 800;
    }

    @Test
    public void updateStateAllBirdsDead() {
        when(population.countLiveBirds()).thenReturn(0);

        gameService.updateState();

        verify(pipeFactory, times(1)).clearPipeArray();
        verify(ui, times(1)).resetScore();
        verify(population, times(1)).nextGeneration();
    }

    @Test
    public void updateStateBirdsAlive() {
        when(population.countLiveBirds()).thenReturn(1);

        gameService.updateState();

        verify(population, never()).nextGeneration();
    }

    @Test
    public void nextGeneration() {
        gameService.nextGeneration();

        verify(pipeFactory, times(1)).clearPipeArray();
        verify(ui, times(1)).resetScore();
        verify(population, times(1)).nextGeneration();
    }

    @Test
    void updateStateBirdOutOfScreen() {
        SmartBird birdTop = mock(SmartBird.class);
        when(birdTop.getY()).thenReturn(10.0);
        when(birdTop.getRadius()).thenReturn(15.0);
        when(birdTop.isDead()).thenReturn(false);

        SmartBird birdBottom = mock(SmartBird.class);
        when(birdBottom.getY()).thenReturn(790.0);
        when(birdBottom.getRadius()).thenReturn(15.0);
        when(birdBottom.isDead()).thenReturn(false);

        List<SmartBird> birds = new ArrayList<>();
        birds.add(birdTop);
        birds.add(birdBottom);
        when(population.getSmartBirds()).thenReturn(birds);

        gameService.updateState();

        verify(birdTop).dead();
        verify(birdBottom).dead();
    }

    @Test
    void updateStateBirdCollidesWithPipe() {
        SmartBird bird = mock(SmartBird.class);
        when(bird.getX()).thenReturn(100.0);
        when(bird.getY()).thenReturn(150.0);
        when(bird.getRadius()).thenReturn(15.0);
        when(bird.isDead()).thenReturn(false);

        Pipe pipe = mock(Pipe.class);
        when(pipe.getX()).thenReturn(100.0);
        when(pipe.getY()).thenReturn(100.0);
        when(pipe.getWidth()).thenReturn(50.0);
        when(pipe.getHoleSize()).thenReturn(20.0);

        List<SmartBird> birds = new ArrayList<>();
        birds.add(bird);

        ArrayList<Pipe> pipes = new ArrayList<>();
        pipes.add(pipe);

        when(population.getSmartBirds()).thenReturn(birds);
        when(pipeFactory.getPipes()).thenReturn(pipes);

        gameService.updateState();

        verify(bird).dead();
    }
}
