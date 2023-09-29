package com.birds.nns;

import java.util.concurrent.ThreadLocalRandom;

public class Nns {

    public final int numbersOfBird = 5000;
    public final int numbersOfNeurons = 6;
    public final int numbersOfParametrs = 4;

    private float[][] neuronsIn = new float[numbersOfBird][numbersOfParametrs];                         //1-number bird 2-data bird ( 0-hight 1-hole 2-accseleration 3-speed)
    private float[][][] neuronsWeightHidenIn = new float[numbersOfBird][numbersOfParametrs+1][numbersOfNeurons];         //1-number birds 2 weight neuronsIn 3 valueHidenNeurons
    private float[][] neuronsValueHiden = new float[numbersOfBird][numbersOfNeurons];
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

    public void updateValueBird(int numberBird, double distanceX, double distanceTopY, double distanceBotY, double speed ) {           //Do normalization !!!!
        neuronsIn[numberBird][0]= (float) (distanceTopY/200.0f-1.0f);
        neuronsIn[numberBird][1]=(float) (distanceX/200.0f-1.0f);
        neuronsIn[numberBird][2]=(float) (distanceBotY/200.0f-1.0f);
        neuronsIn[numberBird][3]= (float) (speed/5.0f-1.0f);
    }

    public boolean isJump(int numberBird){

        for(int i=0;i<numbersOfNeurons; i++){
            neuronsValueHiden[numberBird][i]=0;
            for(int j=0;j<numbersOfParametrs;j++){
                neuronsValueHiden[numberBird][i]+=neuronsIn[numberBird][j]*neuronsWeightHidenIn[numberBird][j][i];
            }
            neuronsValueHiden[numberBird][i]+=neuronsWeightHidenIn[numberBird][numbersOfParametrs][i];
            if(neuronsValueHiden[numberBird][i]<0)neuronsValueHiden[numberBird][i]=0;
        }
        float exitValue=0.0f;

        for(int i=0;i<numbersOfNeurons;i++){
            exitValue+=neuronsValueHiden[numberBird][i]*neuronsWeightHidenOut[numberBird][i];
        }
        exitValue+=neuronsWeightHidenOut[numberBird][numbersOfNeurons];
//        System.out.print(exitValue);
        if (exitValue>0.5f)return true;

        return false;
    }

    void nextGeneration(int best1, int best2){


            for (int j = 0; j < (numbersOfParametrs + 1); j++) {
                for (int k = 0; k < numbersOfNeurons; k++) {
                    neuronsWeightHidenIn[0][j][k] = neuronsWeightHidenIn[best1][j][k];
                    neuronsWeightHidenIn[numbersOfBird-1][j][k] = neuronsWeightHidenIn[best1][j][k];
                }
            }


        for(int i = 0; i<numbersOfBird;i++) {
            for (int j = 0; j < (numbersOfParametrs + 1); j++) {
                for (int k = 0; k < numbersOfNeurons; k++) {
                    neuronsWeightHidenIn[i][j][k] =neuronsWeightHidenIn[0][j][k]
                            +Math.abs(neuronsWeightHidenIn[0][j][k]-neuronsWeightHidenIn[numbersOfBird-1][j][k])
                            *((float) i/numbersOfBird);
                    neuronsWeightHidenIn[i][j][k]= mutateGen(neuronsWeightHidenIn[i][j][k]);
                }
            }
        }

        for(int i = 0; i<numbersOfBird;i++){
            neuronsWeightHidenOut[0]=neuronsWeightHidenOut[best1];
            neuronsWeightHidenOut[numbersOfBird-1]=neuronsWeightHidenOut[best2];
        }

        for(int i = 0; i<numbersOfBird;i++)
            for(int j = 0; j<(numbersOfNeurons+1); j++) {
                neuronsWeightHidenOut[i][j] = neuronsWeightHidenOut[i][j] = neuronsWeightHidenOut[0][j]
                        + Math.abs(neuronsWeightHidenOut[0][j] - neuronsWeightHidenOut[numbersOfBird - 1][j])
                        * ((float) i / numbersOfBird);
                neuronsWeightHidenOut[i][j]=mutateGen(neuronsWeightHidenOut[i][j]);
            }

    }

    private float mutateGen(float genValue) {
        if(ThreadLocalRandom.current().nextFloat()<0.1f) return (ThreadLocalRandom.current().nextFloat()*2.0f-1.0f);
        return genValue;
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
