package com.birds.nn.neuralNetwork;

import com.birds.nn.gameCore.gameObjects.Bird;
import com.birds.nn.gameCore.gameObjects.Pipe;
import com.birds.nn.gameCore.gameObjects.PipeFactory;
import com.birds.nn.neuralNetwork.neuralNetworkCore.NeuralNetwork;
import com.birds.nn.utils.Config;

import java.util.ArrayList;

public class SmartBird extends Bird {

    private final NeuralNetwork neuralNetwork;
    private final PipeFactory pipeFactory;
    private final Config config;

    public SmartBird(PipeFactory pipeFactory, Config config, NeuralNetwork neuralNetwork) {
        super(config);
        this.pipeFactory = pipeFactory;
        this.config = config;
        this.neuralNetwork = neuralNetwork;
    }

    public void updateState() {
        super.updateState();
        if (isJump(pipeFactory.getPipes(), config.game.gameWidth, config.game.gameHeight)) super.tap();
    }

    /**
     * Checks if the bird should jump based on neural network prediction.
     *
     * @param pipes  The list of pipes currently on the screen.
     * @param width  The width of the game screen.
     * @param height The height of the game screen.
     * @return true if the bird should jump, false otherwise.
     *
     * <p>Activates the neural network with the following normalized inputs:</p>
     * <ul>
     * <li>Distance from the bird to the pipe horizontally: {@code (pipe.getX() - getX() + getRadius()) / width}</li>
     * <li>Distance from the bird to the bottom pipe vertically: {@code (getY() - getRadius() - (pipe.getY() + pipe.getHoleSize())) / height}</li>
     * <li>Distance from the bird to the top pipe vertically: {@code (getY() + getRadius() - (pipe.getY() + pipe.getHoleSize())) / height}</li>
     * <li>Bird's speed: {@code getSpeed() / getMaxSpeed()}</li>
     * </ul>
     */
    private boolean isJump(ArrayList<Pipe> pipes, double width, double height) {
        Pipe pipe = nearestPipe(pipes);
        return neuralNetwork.activate(new double[]{
                (pipe.getX() - getX() + getRadius())
                        / width,
                (getY() - getRadius() - (pipe.getY() + pipe.getHoleSize()))
                        / height,
                (getY() + getRadius() - (pipe.getY() + pipe.getHoleSize()))
                        / height,
                getSpeed()
                        / getMaxSpeed()
        })[0] > 0.5;
    }

    /**
     * Finds the nearest pipe to the bird's current position.
     *
     * @param pipes The list of pipes to search.
     * @return The nearest Pipe object, or a default Pipe if all pipes on the screen are passed.
     */
    private Pipe nearestPipe(ArrayList<Pipe> pipes) {
        Pipe minPipe = null;
        for (Pipe pipe : pipes) {
            if (pipe.getX() + pipe.getWidth() > getX() - getRadius()) {
                if (minPipe == null || pipe.getX() < minPipe.getX()) {
                    minPipe = pipe;
                }
            }
        }
        return (minPipe != null) ? minPipe : pipeFactory.createPipe();
    }

    public NeuralNetwork getNeuralNetwork() {
        return neuralNetwork;
    }
}
