package com.birds.nn.gameCore.gameObjects;

import com.birds.nn.utils.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PipeTest {

    @Mock
    private Config config;

    @Mock
    private Config.GameConfig gameConfig;

    @Mock
    private Config.GameConfig.PipeConfig pipeConfig;

    private Pipe pipe;

    @BeforeEach
    public void setUp() {
        config.game = gameConfig;
        gameConfig.pipeConfig = pipeConfig;

        pipeConfig.width = 10;
        pipeConfig.speed = 10;
        pipeConfig.holeSize = 10;

        pipe = new Pipe(config);
    }

    @Test
    void initialState() {
        assertEquals(0, pipe.getX());
        assertEquals(0, pipe.getY());
        assertEquals(10, pipe.getHoleSize());
        assertEquals(10, pipe.getWidth());
    }

    @Test
    void updateStateUpdatesPositionX() {
        pipe.updateState();

        assertTrue(pipe.getX() < 0);
    }

    @Test
    void isOutOfBoundsReturnTrueWhenOutOfBounds() {
        pipe.setX(-100);

        assertTrue(pipe.isOutOfBounds());
    }

    @Test
    void isOutOfBoundsReturnFalseWhenInBounds() {
        assertFalse(pipe.isOutOfBounds());
    }

    @Test
    void setRandomPositionUpdatePosition() {
        pipe.setRandomPosition(100, 100);

        assertEquals(100, pipe.getX());
        assertTrue(pipe.getY() >= 0 && pipe.getY() <= 100);
    }
}