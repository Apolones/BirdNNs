package com.birds.nn.gameCore.gameObjects;

import com.birds.nn.utils.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BirdTest {

    @Mock
    private Config config;

    @Mock
    private Config.GameConfig game;

    @Mock
    private Config.GameConfig.BirdConfig birdConfig;

    private Bird bird;

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

        bird = new Bird(config);
    }

    @Test
    void initialState() {
        assertEquals(0, bird.getX());
        assertEquals(0, bird.getY());
        assertEquals(0, bird.getSpeed());
        assertEquals(-15, bird.getHeadAngle());
        assertEquals(0, bird.getScore());
        assertFalse(bird.isDead());
        assertEquals(10.0, bird.getRadius());
    }

    @Test
    void updateStateIncreasesSpeedAndUpdatesPosition() {
        bird.updateState();

        assertTrue(bird.getSpeed() > 0);
        assertTrue(bird.getY() > 0);
        assertEquals(1, bird.getScore());
    }

    @Test
    void updateStateDoesNotIncreaseSpeedBeyondMax() {
        for (int i = 0; i < 100; i++) {
            bird.updateState();
        }

        assertEquals(bird.getMaxSpeed(), bird.getSpeed(), 0.01);
    }

    @Test
    void updateStateUpdatesHeadAngle() {
        bird.updateState();
        double initialHeadAngle = bird.getHeadAngle();

        for (int i = 0; i < 10; i++) {
            bird.updateState();
        }

        assertTrue(bird.getHeadAngle() > initialHeadAngle);
    }

    @Test
    void tapSetsNegativeSpeed() {
        bird.tap();

        assertEquals(-10.0, bird.getSpeed());
    }

    @Test
    void deadSetsIsDeadTrue() {
        bird.dead();

        assertTrue(bird.isDead());
    }

    @Test
    void updateStateDoesNotChangeStateWhenDead() {
        bird.dead();
        double initialY = bird.getY();
        double initialSpeed = bird.getSpeed();
        long initialScore = bird.getScore();

        bird.updateState();

        assertEquals(initialY, bird.getY());
        assertEquals(initialSpeed, bird.getSpeed());
        assertEquals(initialScore, bird.getScore());
    }
}
