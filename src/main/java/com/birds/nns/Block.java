package com.birds.nns;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class Block {
    double x;
    double y;
    Image image;

    public Block(double x, double y, Image image) {
        this.x = x;
        this.y = y;
        this.image=image;
    }

    void Render(GraphicsContext context) {

    }

    void UpdateState(){

    }
}