package com.birds.nn.gameCore.gameLoop;

import com.birds.nn.gameCore.gameLogic.GameService;
import com.birds.nn.gameCore.gameObjects.Background;
import com.birds.nn.gameCore.gameObjects.Block;
import com.birds.nn.gameCore.gameObjects.PipeFactory;
import com.birds.nn.gameCore.gameObjects.UI;
import com.birds.nn.graphics.Render;
import com.birds.nn.neuralNetwork.Population;
import com.birds.nn.utils.Config;
import com.birds.nn.utils.Formatter;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.springframework.stereotype.Controller;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class MainController {
    private Timeline timeline;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Canvas mainCanvas;
    @FXML
    private Slider speedSlider;
    @FXML
    private CheckBox hideHitbox;
    @FXML
    private TextField startPositionX, startPositionY, gravity, maxSpeed, tapSpeed, distanceBetweenPipe, pipeSpeed, pipeWidth, pipeHoleSize;

    private static final Logger LOGGER = Logger.getLogger(MainController.class.getName());

    private final Config config;
    private final Background background;
    private final PipeFactory pipeFactory;
    private final Population population;
    private final UI ui;
    private final Render render;
    private final GameService gameService;

    public MainController(Config config, Background background, PipeFactory pipeFactory, Population population, UI ui, Render render, GameService gameService) {
        this.config = config;
        this.background = background;
        this.pipeFactory = pipeFactory;
        this.population = population;
        this.ui = ui;
        this.render = render;
        this.gameService = gameService;
    }

    @FXML
    void initialize() {
        try {
            timeline = new Timeline(new KeyFrame(
                    Duration.millis(40),
                    this::onTimeTick
            ));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
            rootPane.widthProperty().addListener((observable, oldValue, newValue) -> resizeCanvas());
            rootPane.heightProperty().addListener((observable, oldValue, newValue) -> resizeCanvas());
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
            gameService.updateState();
            background.updateState();
            pipeFactory.updateState();
            population.updateState();
            ui.updateState();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred while updating the game", e);
        }
    }

    private void setUserSettings() {
        timeline.setRate(speedSlider.getValue());
        Block.setHideHitbox(!hideHitbox.isSelected());
    }

    @FXML
    private void nextGen() {
        gameService.nextGeneration();
    }

    @FXML
    private void save() {
        config.game.birdConfig.startPositionX = Double.parseDouble(startPositionX.getText());
        config.game.birdConfig.startPositionY = Double.parseDouble(startPositionY.getText());
        config.game.birdConfig.gravity = Double.parseDouble(gravity.getText());
        config.game.birdConfig.maxSpeed = Double.parseDouble(maxSpeed.getText());
        config.game.birdConfig.tapSpeed = Double.parseDouble(tapSpeed.getText());
        config.game.pipeConfig.distanceBetweenPipe = Double.parseDouble(distanceBetweenPipe.getText());
        config.game.pipeConfig.speed = Double.parseDouble(pipeSpeed.getText());
        config.game.pipeConfig.width = Double.parseDouble(pipeWidth.getText());
        config.game.pipeConfig.holeSize = Double.parseDouble(pipeHoleSize.getText());
        setUpTextField();
        nextGen();
    }

    private void setUpTextField() {
        setUpTextFieldLimits();
        startPositionX.setText(String.valueOf(config.game.birdConfig.startPositionX));
        startPositionY.setText(String.valueOf(config.game.birdConfig.startPositionY));
        gravity.setText(String.valueOf(config.game.birdConfig.gravity));
        maxSpeed.setText(String.valueOf(config.game.birdConfig.maxSpeed));
        tapSpeed.setText(String.valueOf(config.game.birdConfig.tapSpeed));
        distanceBetweenPipe.setText(String.valueOf(config.game.pipeConfig.distanceBetweenPipe));
        pipeSpeed.setText(String.valueOf(config.game.pipeConfig.speed));
        pipeWidth.setText(String.valueOf(config.game.pipeConfig.width));
        pipeHoleSize.setText(String.valueOf(config.game.pipeConfig.holeSize));
    }

    private void setUpTextFieldLimits() {
        startPositionX.setTextFormatter(Formatter.createDoubleTextFormatter(0, mainCanvas.getWidth()));
        startPositionY.setTextFormatter(Formatter.createDoubleTextFormatter(0, mainCanvas.getHeight()));
        gravity.setTextFormatter(Formatter.createDoubleTextFormatter(0, 100));
        maxSpeed.setTextFormatter(Formatter.createDoubleTextFormatter(0, 100));
        tapSpeed.setTextFormatter(Formatter.createDoubleTextFormatter(0, config.game.birdConfig.maxSpeed));
        distanceBetweenPipe.setTextFormatter(Formatter.createDoubleTextFormatter(0, mainCanvas.getWidth()));
        pipeSpeed.setTextFormatter(Formatter.createDoubleTextFormatter(0, 10));
        pipeWidth.setTextFormatter(Formatter.createDoubleTextFormatter(0, mainCanvas.getWidth()));
        pipeHoleSize.setTextFormatter(Formatter.createDoubleTextFormatter(0, mainCanvas.getHeight() / 2 - 20));
    }

    private void resizeCanvas() {
        config.game.windowConfig.gameWidth = rootPane.getWidth() - 100;
        config.game.windowConfig.gameHeight = rootPane.getHeight();
        mainCanvas.setWidth(config.game.windowConfig.gameWidth);
        mainCanvas.setHeight(config.game.windowConfig.gameHeight);
        setUpTextField();
    }

}
