package com.birds.nns;

public class NNs {

    private final int numbersOfBird = 5;
    private float[][] neuronsIn = new float[numbersOfBird][4];                         //1-number bird 2-data bird ( 1-hight 2-hole 3-accseleration 4-speed)
    private float[][][] neuronsWeightHidenIn = new float[numbersOfBird][5][6];         //1-number birds 2 weight neuronsIn 3 valueHidenNeurons
    private float[][] neuronsWeightHidenOut = new float[numbersOfBird][7];             //1-number birds 2 weight neuronsOut

    public NNs() {

    }
}
