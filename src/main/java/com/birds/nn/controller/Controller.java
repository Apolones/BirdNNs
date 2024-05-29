package com.birds.nn.controller;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.birds.nn.model.*;
import com.birds.nn.view.MainApplication;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class Controller {

    ArrayList<Pipe> pipeBlock = new ArrayList<>();
    private final Population population = new Population(
            MainApplication.getConfig().neuralNetwork.populationSize,
            MainApplication.getConfig().neuralNetwork.inputSize,
            MainApplication.getConfig().neuralNetwork.hiddenLayers,
            MainApplication.getConfig().neuralNetwork.outputSize);
    @FXML
    private Slider speedSlider;
    @FXML
    private CheckBox hideHitbox;
    @FXML
    private Canvas mainCanvas;
    private Background background;
    private Timeline timeline;
    private long score = 0;
    private long maxScore = 0;

    @FXML
    void initialize() {
        timeline = new Timeline(new KeyFrame(
                Duration.millis(40),
                this::onTimeTick
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        background = new Background(mainCanvas.getWidth(), mainCanvas.getHeight());
    }

    private void onTimeTick(ActionEvent actionEvent) {
        UpdateState();
        Render();
    }

    private void Render() {
        GraphicsContext graphicsContext2D = mainCanvas.getGraphicsContext2D();
        background.Render(mainCanvas.getGraphicsContext2D());
        for (Pipe block : pipeBlock) {
            block.Render(graphicsContext2D);
        }

        for (SmartBird smartBird : population.getSmartBirds()) {
            smartBird.Render(graphicsContext2D);
        }

        RenderUI(graphicsContext2D);
    }

    private void UpdateState() {
        setUserSettings();
        generatePipes();
        score++;

        for (Pipe pipe : pipeBlock) {
            pipe.UpdateState();
        }

        background.UpdateState();

        for (int i = 0; i < population.getSmartBirds().size(); i++) {
            population.getSmartBirds().get(i).UpdateState(pipeBlock);
            if (population.getSmartBirds().get(i).isJump(pipeBlock, mainCanvas.getWidth(), mainCanvas.getHeight()))
                population.getSmartBirds().get(i).Tap();
        }
        removePassedPipe();
        isNextGen();
    }

    private void RenderUI(GraphicsContext graphicsContext2D) {
        graphicsContext2D.setFill(Color.DARKRED);
        graphicsContext2D.setFont(Font.font(15));
        graphicsContext2D.fillText("Generation: " + population.getGeneration(), 350, 20);
        graphicsContext2D.fillText("Maximum score: " + maxScore / 100, 350, 40);

        graphicsContext2D.setFont(Font.font(25));
        graphicsContext2D.fillText("Score: " + score / 100, 30, 30);

    }

    private void removePassedPipe() {
        if (!pipeBlock.isEmpty()) {
            pipeBlock.removeIf(pipe -> (pipe.getX() + Pipe.getWidth()) <= 0);
        }
    }

    private void setUserSettings() {
        timeline.setRate(speedSlider.getValue());
        Block.setHideHitbox(!hideHitbox.isSelected());
    }

    private void isNextGen() {
        for (SmartBird smartBird : population.getSmartBirds()) {
            if (!smartBird.isDead()) return;
        }
        resetGame();
    }

    private void generatePipes() {
        if (!pipeBlock.isEmpty()) {
            Pipe pipeLast = pipeBlock.get(pipeBlock.size() - 1);
            if (pipeLast.getX() > (mainCanvas.getWidth() / 1.7)) return;
        }
        int y = ThreadLocalRandom.current().nextInt(100, (int) mainCanvas.getHeight() - 100);
        Pipe pipe = new Pipe(mainCanvas.getWidth(), y);
        pipeBlock.add(pipe);
    }

    @FXML
    public void resetGame() {
        pipeBlock.clear();
        if (score > maxScore) maxScore = score;
        score = 0;
        population.evolve(MainApplication.getConfig().neuralNetwork.mutationRate, MainApplication.getConfig().neuralNetwork.eliteCounter);
    }
}
