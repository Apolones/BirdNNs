package com.birds.nns;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class Controller {

    ArrayList<Pipe> pipeBlock = new ArrayList<>();
    ArrayList<Bird> birdBlock = new ArrayList<>();
    private final Nns nns = new Nns();
    @FXML
    private Button jump;
    @FXML
    private Slider speedSlider;
    @FXML
    private ToggleButton manualPlay;
    @FXML
    private CheckBox hideHitbox;
    @FXML
    private Canvas mainCanvas;
    private Block background;
    Timeline timeline;

    Image birdImage;
    Image pipeImage;
//    Image background;
    private long score=0;
    private long maxScore=0;

    @FXML
    void initialize() {
        timeline = new Timeline(new KeyFrame(
                Duration.millis(40),
                this::onTimeTick
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        loadImage();
        initBlocks();
    }

    private void initBlocks() {
        if(manualPlay.isSelected())generateBirds(1);
        else generateBirds(nns.numbersOfBird);
    }

    private void onTimeTick(ActionEvent actionEvent) {
        UpdateState();
        Render();
    }

    private void Render() {
        GraphicsContext graphicsContext2D = mainCanvas.getGraphicsContext2D();

        drawBackground(graphicsContext2D);

        for (Pipe block : pipeBlock) {
            block.Render(graphicsContext2D);
        }

        for (Bird block : birdBlock) {
            block.Render(graphicsContext2D);
        }
        graphicsContext2D.setFill(Color.DARKRED);
        graphicsContext2D.setFont(Font.font(15));
        graphicsContext2D.fillText("Generation: "+nns.generation, 350,20);
        graphicsContext2D.fillText("Maximum score: " + maxScore/100, 350,40);

        graphicsContext2D.setFont(Font.font(25));
        graphicsContext2D.fillText("Score: " + score/100, 30,30);

    }

    private void UpdateState() {
        setUserSettings();
        generatePipes();
        score++;

        for (Pipe pipe : pipeBlock) {
            pipe.UpdateState();
        }

        for (int i = 0; i<birdBlock.size(); i++) {
            birdBlock.get(i).UpdateState(pipeBlock.get(0));
            nns.updateValueBird(i,
                    pipeBlock.get(0).x-birdBlock.get(i).x-Bird.radius,                                                      //distance to hole on X
                    birdBlock.get(i).y-(pipeBlock.get(0).y-pipeBlock.get(0).getHole())-Bird.radius,                                 //distance to hole on y upper
                    birdBlock.get(i).y-(pipeBlock.get(0).y+pipeBlock.get(0).getHole())+Bird.radius,                                 //distance to hole on y bottom
                    birdBlock.get(i).getSpeed());                                                                                   //bird speed
            if(!manualPlay.isSelected() && nns.isJump(i))birdBlock.get(i).Tap();
        }

        Pipe pipe = pipeBlock.get(0);
        if((pipe.x+ pipe.getWidth())<=0)pipeBlock.remove(0);

        areBirdsDead();
    }

    private void loadImage() {
        background = new Block(0,0,new Image("com/birds/nns/background.png"));
        birdImage = new Image("com/birds/nns/bird.png");
        pipeImage = new Image("com/birds/nns/pipe.png");
    }

    private void setUserSettings() {
        timeline.setRate(speedSlider.getValue());
        Bird.hideHitbox=!hideHitbox.isSelected();
        Pipe.hideHitbox=!hideHitbox.isSelected();

        if(manualPlay.isFocused() && manualPlay.isSelected()){
            nns.generation=0;
            nns.bestScore=0;
        }
        jump.setVisible(manualPlay.isSelected());

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
        if (!pipeBlock.isEmpty()) {
            Pipe pipeLast = pipeBlock.get(pipeBlock.size() - 1);
            if (pipeLast.x > (mainCanvas.getWidth() / 1.7)) return;
        }
        int y = ThreadLocalRandom.current().nextInt(100, (int) mainCanvas.getHeight() - 100);
        Pipe pipe = new Pipe(mainCanvas.getWidth(),y, pipeImage);
        pipeBlock.add(pipe);

    }

    public void generateBirds(int number){
        for(int i = 0; i < number; i++){
            Bird bird = new Bird(Bird.scaleImage*Bird.radius, mainCanvas.getHeight()/2 , birdImage);
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

    private void drawBackground(GraphicsContext graphicsContext2D) {
        background.x-=0.1;
        if(background.x<=-mainCanvas.getWidth())background.x=0;
        graphicsContext2D.drawImage( background.image, background.x, background.y, mainCanvas.getWidth(), mainCanvas.getHeight());
        graphicsContext2D.drawImage( background.image,mainCanvas.getWidth()+ background.x, background.y, mainCanvas.getWidth(), mainCanvas.getHeight());
    }

    @FXML
    void JumpBird() {
        if(birdBlock.size()>1)resetGame();
        Bird bird = birdBlock.get(0);
        bird.Tap();
    }
}
