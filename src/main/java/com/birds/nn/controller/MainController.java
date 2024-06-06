package com.birds.nn.controller;

import com.birds.nn.utils.Config;
import com.birds.nn.model.*;
import com.birds.nn.utils.Formatter;
import com.birds.nn.view.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.logging.Level;
import java.util.logging.Logger;
@Controller
public class MainController {
    private Timeline timeline;

    @FXML
    private Canvas mainCanvas;
    @FXML
    private Slider speedSlider;
    @FXML
    private CheckBox hideHitbox;
    @FXML
    private TextField startPositionX, startPositionY, acceleration, maxSpeed, tapSpeed, pipeCount, pipeSpeed, pipeWidth, pipeHoleSize;

    @Autowired
    private Config config;

    @Autowired
    private Background background;

    @Autowired
    private PipeFactory pipeFactory;

    @Autowired
    private Population population;

    @Autowired
    private UI ui;

    @Autowired
    private Render render;

    private static final Logger LOGGER = Logger.getLogger(MainController.class.getName());

    @FXML
    void initialize() {
        try {
            timeline = new Timeline(new KeyFrame(
                    Duration.millis(40),
                    this::onTimeTick
            ));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
            setUpTextField();
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
            render.render(graphicsContext2D, background);
            render.render(graphicsContext2D, pipeFactory.getPipes());
            render.render(graphicsContext2D, population.getSmartBirds());
            render.render(graphicsContext2D, ui);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred while rendering the game", e);
        }
    }

    private void updateState() {
        try {
            setUserSettings();
            background.updateState(mainCanvas.getWidth());
            pipeFactory.updateState(mainCanvas.getWidth(), mainCanvas.getHeight());
            population.updateState(pipeFactory, mainCanvas.getWidth(), mainCanvas.getHeight());
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
        pipeFactory.clearPipeArray();
        ui.nextGeneration();
    }

    @FXML
    private void nextGen() {
        for (SmartBird smartBird : population.getSmartBirds()) {
            smartBird.dead();
        }
        if (population.isNextGen()) resetGame();
    }

    @FXML
    private void save() {
        config.game.birdConfig.startPositionX = Double.parseDouble(startPositionX.getText());
        config.game.birdConfig.startPositionY = Double.parseDouble(startPositionY.getText());
        config.game.birdConfig.acceleration = Double.parseDouble(acceleration.getText());
        config.game.birdConfig.maxSpeed = Double.parseDouble(maxSpeed.getText());
        config.game.birdConfig.tapSpeed = Double.parseDouble(tapSpeed.getText());
        config.game.pipeConfig.count = Double.parseDouble(pipeCount.getText());
        config.game.pipeConfig.speed = Double.parseDouble(pipeSpeed.getText());
        config.game.pipeConfig.width = Double.parseDouble(pipeWidth.getText());
        config.game.pipeConfig.holeSize = Double.parseDouble(pipeHoleSize.getText());
       // Bird.updateStatic();
        // Pipe.updateStatic();
        //PipeFactory.updateStatic();
        setUpTextField();
        nextGen();
    }

    private void setUpTextField() {
        startPositionX.setTextFormatter(Formatter.createDoubleTextFormatter(0, mainCanvas.getWidth()));
        startPositionY.setTextFormatter(Formatter.createDoubleTextFormatter(0, mainCanvas.getHeight()));
        acceleration.setTextFormatter(Formatter.createDoubleTextFormatter(0, 100));
        maxSpeed.setTextFormatter(Formatter.createDoubleTextFormatter(0, 100));
        tapSpeed.setTextFormatter(Formatter.createDoubleTextFormatter(0, config.game.birdConfig.maxSpeed));
        pipeCount.setTextFormatter(Formatter.createDoubleTextFormatter(0, 10));
        pipeSpeed.setTextFormatter(Formatter.createDoubleTextFormatter(0, 10));
        pipeWidth.setTextFormatter(Formatter.createDoubleTextFormatter(0, mainCanvas.getWidth()));
        pipeHoleSize.setTextFormatter(Formatter.createDoubleTextFormatter(0, mainCanvas.getHeight()/2 - 20));


        startPositionX.setText(String.valueOf(config.game.birdConfig.startPositionX));
        startPositionY.setText(String.valueOf(config.game.birdConfig.startPositionY));
        acceleration.setText(String.valueOf(config.game.birdConfig.acceleration));
        maxSpeed.setText(String.valueOf(config.game.birdConfig.maxSpeed));
        tapSpeed.setText(String.valueOf(config.game.birdConfig.tapSpeed));
        pipeCount.setText(String.valueOf(config.game.pipeConfig.count));
        pipeSpeed.setText(String.valueOf(config.game.pipeConfig.speed));
        pipeWidth.setText(String.valueOf(config.game.pipeConfig.width));
        pipeHoleSize.setText(String.valueOf(config.game.pipeConfig.holeSize));
    }
}
