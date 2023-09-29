package com.birds.nns;

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
import javafx.scene.paint.Color;
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
    private Canvas mainCanvas;
    @FXML
    void JumpBird(ActionEvent event) {
        Bird bird = (Bird) birdBlock.get(0);
        bird.Tap();
    }

    @FXML
    void initialize() {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(5),
                this::onTimeTick
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        initBlocks();
//       nns.check();
    }

    private void initBlocks() {
        generateBirds(nns.numbersOfBird);

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

        for (Block block : pipeBlock) {
            block.Render(graphicsContext2D);
        }

        for (Block block : birdBlock) {
            block.Render(graphicsContext2D);
        }
    }

    private void UpdateState() {
        
        generatePipes();
        
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
            if(nns.isJump(i))birdBlock.get(i).Tap();
        }

        Pipe pipe = pipeBlock.get(0);
        if(pipe.x<=0)pipeBlock.remove(0);

        areBirdsDead();
    }

    private void areBirdsDead() {
        for (Bird bird: birdBlock) {
            if(!bird.isDead()) return;
        }
        int best1=choiceBestBird();
        birdBlock.get(best1).score=0;
        int best2=choiceBestBird();
        nns.nextGeneration(best1,best2);
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
        initBlocks();

    }

}
