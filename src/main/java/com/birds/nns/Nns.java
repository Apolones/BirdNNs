package com.birds.nns;

import java.util.concurrent.ThreadLocalRandom;

public class Nns {

    public final int numbersOfBird = 1;
    public final int numbersOfNeurons = 6;
    public final int numbersOfParametrs = 4;

    private float[][] neuronsIn = new float[numbersOfBird][numbersOfParametrs];                         //1-number bird 2-data bird ( 0-hight 1-hole 2-accseleration 3-speed)
    private float[][][] neuronsWeightHidenIn = new float[numbersOfBird][numbersOfParametrs+1][numbersOfNeurons];         //1-number birds 2 weight neuronsIn 3 valueHidenNeurons
    private float[][] neuronsWeightHidenOut = new float[numbersOfBird][numbersOfNeurons+1];             //1-number birds 2 weight neuronsOut

    public Nns() {

        for(int i = 0; i<numbersOfBird;i++)
            for(int j = 0; j<(numbersOfParametrs+1); j++)
                for (int k =0; k<numbersOfNeurons;k++)
                    neuronsWeightHidenIn[i][j][k]=ThreadLocalRandom.current().nextFloat()*2.0f-1.0f;

        for(int i = 0; i<numbersOfBird;i++)
            for(int j = 0; j<(numbersOfNeurons+1); j++)
                    neuronsWeightHidenOut[i][j]=ThreadLocalRandom.current().nextFloat()*2.0f-1.0f;

    }

    public void updateValueBird(int numberBird, double hole, double hight, double accseleration, double speed ) {           //Do normalization !!!!
        neuronsIn[numberBird][0]=(float) hight;
        neuronsIn[numberBird][1]=(float) hole;
        neuronsIn[numberBird][2]=(float) accseleration;
        neuronsIn[numberBird][3]=(float) speed;
    }



    void check(){
        System.out.print("\nneuronsWeightHidenOut\n");
        for (float[] neurons: neuronsWeightHidenOut
        ) {
            for (float element: neurons
            ) {
                System.out.print(element+" ");
            }
            System.out.print("\n");
        }
        System.out.print("\nneuronsWeightHidenIn\n");
        for (float[][] birds: neuronsWeightHidenIn
        ) {
            for (float[] neurons: birds
            ) {
                for (float element : neurons
                ) {
                    System.out.print(element+" ");
                }
                System.out.print("\n");
            }

        }
    }
}
