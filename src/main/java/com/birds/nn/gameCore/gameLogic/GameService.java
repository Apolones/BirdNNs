package com.birds.nn.gameCore.gameLogic;

import com.birds.nn.gameCore.gameObjects.Bird;
import com.birds.nn.gameCore.gameObjects.Pipe;
import com.birds.nn.gameCore.gameObjects.PipeFactory;
import com.birds.nn.gameCore.gameObjects.UI;
import com.birds.nn.neuralNetwork.Population;
import com.birds.nn.utils.Config;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {
    private final PipeFactory pipeFactory;
    private final Population population;
    private final UI ui;
    private final Config config;

    public GameService(PipeFactory pipeFactory, Population population, UI ui, Config config) {
        this.pipeFactory = pipeFactory;
        this.population = population;
        this.ui = ui;
        this.config = config;
    }

    public void updateState() {
        checkCollision(pipeFactory.getPipes(), population.getSmartBirds(), config.game.gameWidth, config.game.gameHeight);
        if (population.countLiveBirds() == 0) nextGeneration();
    }

    public void nextGeneration() {
        pipeFactory.clearPipeArray();
        ui.resetScore();
        population.nextGeneration();
    }

    private void checkCollision(List<Pipe> pipes, List<? extends Bird> birds, double maxX, double maxY) {
        List<? extends Bird> liveBirds = birds.stream().filter(bird -> !bird.isDead()).toList();

        for (Bird bird : liveBirds) {
            if (bird.getY() <= bird.getRadius() || bird.getY() >= maxY - bird.getRadius() ||
                    bird.getX() <= bird.getRadius() || bird.getX() >= maxX - bird.getRadius()) {
                bird.dead();
                continue;
            }

            List<Pipe> nearestPipes = nearestPipes(pipes, bird.getX() - bird.getRadius(), bird.getX() + bird.getRadius());

            for (Pipe pipe : nearestPipes) {
                if (pipe.getX() < bird.getX() + bird.getRadius()
                        && pipe.getX() + pipe.getWidth() > bird.getX() - bird.getRadius()) {
                    if (bird.getY() + bird.getRadius() > pipe.getY() + pipe.getHoleSize()
                            || bird.getY() - bird.getRadius() < pipe.getY() - pipe.getHoleSize()) {
                        bird.dead();
                    }
                }
            }
        }
    }

    private List<Pipe> nearestPipes(List<Pipe> pipes, double minX, double maxX) {
        List<Pipe> nearestPipes = new ArrayList<>();
        for (Pipe pipe : pipes) {
            if (pipe.getX() + pipe.getWidth() > minX && pipe.getX() - pipe.getWidth() < maxX) {
                nearestPipes.add(pipe);
            }
        }
        return nearestPipes;
    }
}