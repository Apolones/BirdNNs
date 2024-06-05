package com.birds.nn.config;

import java.util.List;

public class Config {
    public NeuralNetworkConfig neuralNetwork;
    public GameConfig game;
    public ResourcesConfig resources;

    public static class NeuralNetworkConfig {
        public int populationSize;
        public int inputSize;
        public List<Integer> hiddenLayers;
        public int outputSize;
        public double mutationRate;
        public int eliteCounter;
    }

    public static class GameConfig {
        public String gameTitle;
        public BirdConfig birdConfig;
        public PipeConfig pipeConfig;
        public BackgroundConfig backgroundConfig;

        public static class BirdConfig {
            public int startPositionX;
            public int startPositionY;
            public double acceleration;
            public double maxSpeed;
            public double tapSpeed;
            public int radius;
            public int scaleImage;
        }

        public static class PipeConfig {
            public double speed;
            public double holeSize;
            public double width;
            public double imageTopHeight;
            public double imageTopWight;
        }

        public static class BackgroundConfig {
            public double speed;
        }
    }

    public static class ResourcesConfig {
        public String backgroundImage;
        public String birdImage;
        public String pipeImage;
        public String iconImage;
    }
}