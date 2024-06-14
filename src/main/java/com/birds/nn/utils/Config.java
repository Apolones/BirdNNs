package com.birds.nn.utils;

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
        public WindowConfig windowConfig;
        public BirdConfig birdConfig;
        public PipeConfig pipeConfig;
        public BackgroundConfig backgroundConfig;

        public static class WindowConfig {
            public double minWidth;
            public double minHeight;
            public double gameWidth;
            public double gameHeight;
        }
        public static class BirdConfig {
            public double startPositionX;
            public double startPositionY;
            public double gravity;
            public double maxSpeed;
            public double tapSpeed;
            public double radius;
            public double scaleImage;
        }

        public static class PipeConfig {
            public double distanceBetweenPipe;
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