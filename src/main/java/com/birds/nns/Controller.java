package com.birds.nns;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class Controller {

    ArrayList<Pipe> pipeBlock = new ArrayList<>();
    ArrayList<Bird> birdBlock = new ArrayList<>();
    private Nns nns = new Nns();
    @FXML
    private Button jump;
    @FXML
    private Button reset;
    @FXML
    private Slider speedSlider;
    @FXML
    private ToggleButton manualPlay;
    @FXML
    private Canvas mainCanvas;
    Timeline timeline;
    private long score=0;
    private long maxScore=0;
    @FXML
    void JumpBird(ActionEvent event) {
        Bird bird = (Bird) birdBlock.get(0);
        bird.Tap();
    }

    @FXML
    void initialize() {
        timeline = new Timeline(new KeyFrame(
                Duration.millis(40),
                this::onTimeTick
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        initBlocks();
        //test func
//       nns.check();
    }

    private void initBlocks() {
        if(manualPlay.isSelected())generateBirds(1);
        else generateBirds(nns.numbersOfBird);

        Pipe pipe = new Pipe(mainCanvas.getWidth()-150, 250);
        pipeBlock.add(pipe);
    }

    private void onTimeTick(ActionEvent actionEvent) {
        UpdateState();
        Render();
    }

    private void Render() {
        GraphicsContext graphicsContext2D = mainCanvas.getGraphicsContext2D();
        graphicsContext2D.setFill(Color.BLUE);
        graphicsContext2D.fillRect(0,0, mainCanvas.getWidth(), mainCanvas.getHeight());

        for (Pipe block : pipeBlock) {
            block.Render(graphicsContext2D);
        }

        for (Bird block : birdBlock) {
            block.Render(graphicsContext2D);
        }

        graphicsContext2D.setFont(Font.font(15));
        graphicsContext2D.fillText(String.valueOf("Generation: "+nns.generation), 350,20);
        graphicsContext2D.fillText(String.valueOf("Maximum score: " + maxScore/100), 350,40);

        graphicsContext2D.setFont(Font.font(25));
        graphicsContext2D.fillText(String.valueOf("Score: " + score/100), 30,30);
    }

    private void UpdateState() {
        if(manualPlay.isFocused() && manualPlay.isSelected()){
            nns.generation=0;
            nns.bestScore=0;
            resetGame();
        }
        generatePipes();
        score++;
        timeline.setRate(speedSlider.getValue());
        
        for (Pipe pipe : pipeBlock) {
            pipe.UpdateState();
        }

        for (int i = 0; i<birdBlock.size(); i++) {
            birdBlock.get(i).UpdateState(pipeBlock.get(0));
            nns.updateValueBird(i,
                    pipeBlock.get(0).x-birdBlock.get(i).x,                                                      //distance to hole on X
                    birdBlock.get(i).y-(pipeBlock.get(0).y-pipeBlock.get(0).getHole()),                                 //distance to hole on y upper
                    birdBlock.get(i).y-(pipeBlock.get(0).y+pipeBlock.get(0).getHole()),                                 //distance to hole on y bottom
                    birdBlock.get(i).getSpeed());                                                                       //bird speed
            if(!manualPlay.isSelected() && nns.isJump(i))birdBlock.get(i).Tap();
        }

        Pipe pipe = pipeBlock.get(0);
        if((pipe.x+ pipe.getWidth())<=0)pipeBlock.remove(0);

        areBirdsDead();
    }

    private void areBirdsDead() {
        for (Bird bird: birdBlock) {
            if(!bird.isDead()) return;
        }
        int best=choiceBestBird();
        nns.nextGeneration(best,birdBlock.get(best).score);
        resetGame();
    }

    private int choiceBestBird() {
        int best=0;
        long score=0;
        for (int i = 0; i<birdBlock.size(); i++) {
            if(birdBlock.get(i).score>score){
                score=birdBlock.get(i).score;
                best=i;
            }
        }
        return best;
    }

    private void generatePipes() {
        Pipe pipeLast = pipeBlock.get(pipeBlock.size()-1);
        if (pipeLast.x>300)return;
        int y = ThreadLocalRandom.current().nextInt(100,300);

        Pipe pipe = new Pipe(mainCanvas.getWidth(),y);
        pipeBlock.add(pipe);
    }

    public void generateBirds(int number){
        for(int i = 0; i < number; i++){
            Bird bird = new Bird(30, 250);
            birdBlock.add(bird);
        }
    }

    public void resetGame(){
        pipeBlock.clear();
        birdBlock.clear();
        if(score>maxScore)maxScore=score;
        score=0;
        initBlocks();

    }

}
