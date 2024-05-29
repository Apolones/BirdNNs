package com.birds.nn.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class Block {
    double x;
    double y;
    Image image;
    static boolean hideHitbox=true;

    public Block(double x, double y, Image image) {
        this.x = x;
        this.y = y;
        this.image=image;
    }

    void Render(GraphicsContext context) {

    }

    void UpdateState(){

    }

    public double getX() {
        return x;
    }

    public void setX(double x){
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public static void setHideHitbox(boolean hideHitbox) {
        Block.hideHitbox = hideHitbox;
    }
}