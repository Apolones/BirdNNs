package com.birds.nn.gameCore.gameObjects;

import com.birds.nn.utils.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PipeFactoryTest {

    @Mock
    private Config config;

    @Mock
    private Config.GameConfig game;

    @Mock
    private Config.GameConfig.WindowConfig windowConfig;

    @Mock
    private Config.GameConfig.PipeConfig pipeConfig;

    @Mock
    private Pipe pipe;

    private PipeFactory pipeFactory;

    @BeforeEach
    void setUp() {
        config.game = game;
        game.windowConfig = windowConfig;
        game.pipeConfig = pipeConfig;

        pipeConfig.width = 10;
        pipeConfig.speed = 10;
        pipeConfig.holeSize = 10;
        pipeConfig.distanceBetweenPipe = 200;

        game.windowConfig.gameWidth = 400;
        game.windowConfig.gameHeight = 400;

        pipeFactory = spy(new PipeFactory(config));
    }

    @Test
    void updateStateGeneratePipes() {
        doReturn(pipe).when(pipeFactory).createPipe();
        pipeFactory.updateState();

        assertFalse(pipeFactory.getPipes().isEmpty());
    }

    @Test
    void updateStateDontGeneratePipesIfTooClose() {
        pipeFactory.getPipes().add(pipe);
        when(pipe.getX()).thenReturn(350.0);

        pipeFactory.updateState();

        assertEquals(1, pipeFactory.getPipes().size());
    }

    @Test
    void updateStateRemoveOutBouncePipe() {
        doReturn(pipe).when(pipeFactory).createPipe();
        pipeFactory.getPipes().add(pipe);
        when(pipe.isOutOfBounds()).thenReturn(true);

        pipeFactory.updateState();

        assertTrue(pipeFactory.getPipes().isEmpty());
    }

    @Test
    void updateStateKeepsInBoundsPipe() {
        doReturn(pipe).when(pipeFactory).createPipe();
        when(pipe.isOutOfBounds()).thenReturn(false);
        pipeFactory.getPipes().add(pipe);

        pipeFactory.updateState();

        assertFalse(pipeFactory.getPipes().isEmpty());
    }

    @Test
    void clearPipeArray() {
        pipeFactory.getPipes().add(pipe);
        pipeFactory.clearPipeArray();

        assertTrue(pipeFactory.getPipes().isEmpty());
    }
}
