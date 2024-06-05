package com.birds.nn.model;

public class UI {
    private long maxScore;
    private long score;
    private int generation;
    private long numBirds;

    public UI() {
        this.maxScore = 0;
        this.score = 0;
        this.generation = 0;
        this.numBirds = 0;
    }

    public void updateState(long numBirds) {
        score++;
        if (score > maxScore) {
            maxScore = score;
        }
        this.numBirds = numBirds;
    }

    public void nextGeneration() {
        generation++;
        resetScore();
    }

    private void resetScore() {
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
