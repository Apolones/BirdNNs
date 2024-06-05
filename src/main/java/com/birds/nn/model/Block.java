package com.birds.nn.model;

public class Block {
    double x;
    double y;
    static boolean hideHitbox = true;

    public Block(double x, double y) {
        this.x = x;
        this.y = y;
    }

    void updateState() {

    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public static boolean showHitbox() {
        return !hideHitbox;
    }

    public static void setHideHitbox(boolean hideHitbox) {
        Block.hideHitbox = hideHitbox;
    }
}