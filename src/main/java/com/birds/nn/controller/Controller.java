package com.birds.nn.controller;

import com.birds.nn.model.*;
import com.birds.nn.view.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.util.Duration;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    @FXML
    private Canvas mainCanvas;
    private Timeline timeline;
    @FXML
    private Slider speedSlider;
    @FXML
    private CheckBox hideHitbox;

    private final Background background = new Background();
    private final PipeArray pipeArray = new PipeArray();
    private final Population population = new Population(
            MainApplication.getConfig().neuralNetwork.populationSize,
            MainApplication.getConfig().neuralNetwork.inputSize,
            MainApplication.getConfig().neuralNetwork.hiddenLayers,
            MainApplication.getConfig().neuralNetwork.outputSize);
    private final UI ui = new UI();

    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());

    @FXML
    void initialize() {
        try {
            timeline = new Timeline(new KeyFrame(
                    Duration.millis(40),
                    this::onTimeTick
            ));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
            LOGGER.info("Game started");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred while starting the game", e);
        }
    }

    private void onTimeTick(ActionEvent actionEvent) {
        updateState();
        render();
    }

    private void render() {
        try {
            GraphicsContext graphicsContext2D = mainCanvas.getGraphicsContext2D();
            BackgroundRender.render(graphicsContext2D, background);
            PipeArrayRender.render(graphicsContext2D, pipeArray);
            PopulationRender.render(graphicsContext2D, population);
            UIRender.render(graphicsContext2D, ui);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred while rendering the game", e);
        }
    }

    private void updateState() {
        try {
            setUserSettings();
            background.updateState(mainCanvas.getWidth());
            pipeArray.updateState(mainCanvas.getWidth(), mainCanvas.getHeight());
            population.updateState(pipeArray, mainCanvas.getWidth(), mainCanvas.getHeight());
            ui.updateState(population.getSmartBirds().stream().filter(smartBird -> !smartBird.isDead()).count());
            if (population.isNextGen()) resetGame();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred while updating the game", e);
        }
    }

    private void setUserSettings() {
        timeline.setRate(speedSlider.getValue());
        Block.setHideHitbox(!hideHitbox.isSelected());
    }

    public void resetGame() {
        pipeArray.clearPipeArray();
        ui.nextGeneration();
    }

    @FXML
    private void nextGen() {
        for (SmartBird smartBird : population.getSmartBirds()) {
            smartBird.dead();
        }
        if (population.isNextGen()) resetGame();
    }
}
