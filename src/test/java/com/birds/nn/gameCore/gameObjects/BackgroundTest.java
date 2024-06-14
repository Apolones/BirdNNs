package com.birds.nn.gameCore.gameObjects;

import com.birds.nn.utils.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BackgroundTest {

    @Mock
    private Config config;

    @Mock
    private Config.GameConfig game;

    @Mock
    private Config.GameConfig.WindowConfig windowConfig;

    @Mock
    private Config.GameConfig.BackgroundConfig backgroundConfig;

    private Background background;

    @BeforeEach
    public void setUp() {
        config.game = game;
        game.windowConfig = windowConfig;
        game.backgroundConfig = backgroundConfig;
        backgroundConfig.speed = 5.0;
        game.windowConfig.gameWidth = 400;
        game.windowConfig.gameHeight = 400;

        background = new Background(config);
    }

    @Test
    void initialState() {
        assertEquals(0, background.getX());
        assertEquals(0, background.getY());
    }

    @Test
    void updateStateUpdatePositionX() {
        background.setX(100.0);
        background.updateState();

        assertEquals(95.0, background.getX());
    }

    @Test
    void updateStateResetsXWhenOutOfBounds() {
        background.setX(-399.0);
        background.updateState();

        assertEquals(0, background.getX());
    }

    @Test
    void updateStateDoesNotResetXWhenInBounds() {
        background.setX(-195.0);
        background.updateState();

        assertEquals(-200.0, background.getX());
    }
}