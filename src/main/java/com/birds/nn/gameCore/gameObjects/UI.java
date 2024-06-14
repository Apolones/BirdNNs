package com.birds.nn.gameCore.gameObjects;

import com.birds.nn.neuralNetwork.Population;
import org.springframework.stereotype.Component;

@Component
public class UI {
    private long maxScore;
    private long score;
    private int generation;
    private long numBirds;
    private final Population population;

    public UI(Population population) {
        this.population = population;
        this.maxScore = 0;
        this.score = 0;
        this.generation = 0;
        this.numBirds = 0;
    }

    public void updateState() {
        score++;
        generation = population.getGeneration();
        numBirds = population.countLiveBirds();
        maxScore = population.getBestScore();
        if (score > maxScore) maxScore = score;
    }

    public void resetScore() {
        score = 0;
    }

    public long getMaxScore() {
        return maxScore;
    }

    public long getScore() {
        return score;
    }

    public int getGeneration() {
        return generation;
    }

    public long getNumBirds() {
        return numBirds;
    }
}
