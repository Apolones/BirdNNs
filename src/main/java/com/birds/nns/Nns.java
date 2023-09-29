package com.birds.nns;

import java.util.concurrent.ThreadLocalRandom;

public class Nns {
    public final int numbersOfBird = 50;
    public final int numbersOfNeurons = 8;
    public final int numbersOfParameters = 4;

    public int generation;

    private float[][] neuronsIn = new float[numbersOfBird][numbersOfParameters];                         //1-number bird 2-data bird ( 0-hight 1-hole 2-accseleration 3-speed)
    private float[][][] neuronsWeightHiddenIn = new float[numbersOfBird][numbersOfParameters +1][numbersOfNeurons];         //1-number birds 2 weight neuronsIn 3 valueHidenNeurons
    private float[][] neuronsValueHidden = new float[numbersOfBird][numbersOfNeurons];
    private float[][] neuronsWeightHiddenOut = new float[numbersOfBird][numbersOfNeurons+1];             //1-number birds 2 weight neuronsOut
    private float[][] bestBirdWeightHiddenIn = new float[numbersOfParameters +1][numbersOfNeurons];
    private float[] bestBirdWeightHiddenOut = new float[numbersOfNeurons+1];
    private long bestScore;
    public Nns() {

        generation=1;

        for(int i = 0; i<numbersOfBird;i++)
            for(int j = 0; j<(numbersOfParameters +1); j++)
                for (int k =0; k<numbersOfNeurons;k++)
                    neuronsWeightHiddenIn[i][j][k]=ThreadLocalRandom.current().nextFloat()*2.0f-1.0f;

        for(int i = 0; i<numbersOfBird;i++)
            for(int j = 0; j<(numbersOfNeurons+1); j++)
                    neuronsWeightHiddenOut[i][j]=ThreadLocalRandom.current().nextFloat()*2.0f-1.0f;

        //generate random best bird
        for(int j = 0; j<(numbersOfParameters +1); j++)
            for (int k =0; k<numbersOfNeurons;k++)
                bestBirdWeightHiddenIn[j][k]=ThreadLocalRandom.current().nextFloat()*2.0f-1.0f;

        for(int j = 0; j<(numbersOfNeurons+1); j++)
            bestBirdWeightHiddenOut[j]=ThreadLocalRandom.current().nextFloat()*2.0f-1.0f;

        bestScore=0;

    }

    public void updateValueBird(int numberBird, double distanceX, double distanceTopY, double distanceBotY, double speed ) {           //Do normalization !!!!
        neuronsIn[numberBird][0]= (float) (distanceTopY/200.0f-1.0f);
        neuronsIn[numberBird][1]=(float) (distanceX/200.0f-1.0f);
        neuronsIn[numberBird][2]=(float) (distanceBotY/200.0f-1.0f);
        neuronsIn[numberBird][3]= (float) (speed/5.0f-1.0f);
    }

    public boolean isJump(int numberBird){

        for(int i=0;i<numbersOfNeurons; i++){
            neuronsValueHidden[numberBird][i]=0;
            for(int j = 0; j< numbersOfParameters; j++){
                neuronsValueHidden[numberBird][i]+=neuronsIn[numberBird][j]* neuronsWeightHiddenIn[numberBird][j][i];
            }
            neuronsValueHidden[numberBird][i]+= neuronsWeightHiddenIn[numberBird][numbersOfParameters][i];
            if(neuronsValueHidden[numberBird][i]<0) neuronsValueHidden[numberBird][i]=0;
        }
        float exitValue=0.0f;

        for(int i=0;i<numbersOfNeurons;i++){
            exitValue+= neuronsValueHidden[numberBird][i]* neuronsWeightHiddenOut[numberBird][i];
        }
        exitValue+= neuronsWeightHiddenOut[numberBird][numbersOfNeurons];
//        System.out.print(exitValue);
        if (exitValue>0.3f)return true;

        return false;
    }

//    void nextGeneration(int best1, int best2){
//
//
//            for (int j = 0; j < (numbersOfParametrs + 1); j++) {
//                for (int k = 0; k < numbersOfNeurons; k++) {
//                    neuronsWeightHidenIn[0][j][k] = neuronsWeightHidenIn[best1][j][k];
//                    neuronsWeightHidenIn[numbersOfBird-1][j][k] = neuronsWeightHidenIn[best2][j][k];
//                }
//            }
//
//
//        for(int i = 0; i<numbersOfBird;i++) {
//            for (int j = 0; j < (numbersOfParametrs + 1); j++) {
//                for (int k = 0; k < numbersOfNeurons; k++) {
//                    neuronsWeightHidenIn[i][j][k] =neuronsWeightHidenIn[0][j][k]
//                            +Math.abs(neuronsWeightHidenIn[0][j][k]-neuronsWeightHidenIn[numbersOfBird-1][j][k])
//                            *((float) i/numbersOfBird);
//                    neuronsWeightHidenIn[i][j][k]= mutateGen(neuronsWeightHidenIn[i][j][k]);
//                }
//            }
//        }
//
//        for(int i = 0; i<(numbersOfNeurons+1);i++){
//            neuronsWeightHidenOut[0][i]=neuronsWeightHidenOut[best1][i];
//            neuronsWeightHidenOut[numbersOfBird-1][i]=neuronsWeightHidenOut[best2][i];
//        }
//
//        for(int i = 0; i<numbersOfBird;i++)
//            for(int j = 0; j<(numbersOfNeurons+1); j++) {
//                neuronsWeightHidenOut[i][j] = neuronsWeightHidenOut[0][j]
//                        + Math.abs(neuronsWeightHidenOut[0][j] - neuronsWeightHidenOut[numbersOfBird - 1][j])
//                        * ((float) i / numbersOfBird);
//                neuronsWeightHidenOut[i][j]=mutateGen(neuronsWeightHidenOut[i][j]);
//            }
//
//    }
//rewrite nextGeneration with new algorithm
    void nextGeneration(int best, long bestScore){

        generation++;

        for (int j = 0; j < (numbersOfParameters + 1); j++) {
            for (int k = 0; k < numbersOfNeurons; k++) {
                neuronsWeightHiddenIn[0][j][k] = bestBirdWeightHiddenIn[j][k];
                neuronsWeightHiddenIn[numbersOfBird-1][j][k] = neuronsWeightHiddenIn[best][j][k];
            }
        }


        for(int i = 1; i<(numbersOfBird-1);i++) {
            for (int j = 0; j < (numbersOfParameters + 1); j++) {
                for (int k = 0; k < numbersOfNeurons; k++) {
                    neuronsWeightHiddenIn[i][j][k] = neuronsWeightHiddenIn[0][j][k]
                            +Math.abs(neuronsWeightHiddenIn[0][j][k]- neuronsWeightHiddenIn[numbersOfBird-1][j][k])
                            *((float) i/numbersOfBird);
                    neuronsWeightHiddenIn[i][j][k]= mutateGen(neuronsWeightHiddenIn[i][j][k]);
                }
            }
        }

        for(int i = 0; i<(numbersOfNeurons+1);i++){
            neuronsWeightHiddenOut[0][i]= bestBirdWeightHiddenOut[i];
            neuronsWeightHiddenOut[numbersOfBird-1][i]= neuronsWeightHiddenOut[best][i];
        }

        for(int i = 1; i<(numbersOfBird-1);i++)
            for(int j = 0; j<(numbersOfNeurons+1); j++) {
                neuronsWeightHiddenOut[i][j] = neuronsWeightHiddenOut[0][j]
                        + Math.abs(neuronsWeightHiddenOut[0][j] - neuronsWeightHiddenOut[numbersOfBird - 1][j])
                        * ((float) i / numbersOfBird);
                neuronsWeightHiddenOut[i][j]=mutateGen(neuronsWeightHiddenOut[i][j]);
            }

        if (bestScore>this.bestScore){
            for (int j = 0; j < (numbersOfParameters + 1); j++) {
                for (int k = 0; k < numbersOfNeurons; k++) {
                    bestBirdWeightHiddenIn[j][k] = neuronsWeightHiddenIn[numbersOfBird-1][j][k] ;
                }
            }

            for(int i = 0; i<(numbersOfNeurons+1);i++){
                bestBirdWeightHiddenOut[i]= neuronsWeightHiddenOut[numbersOfBird-1][i];
            }

        }

    }

    private float mutateGen(float genValue) {
        if(ThreadLocalRandom.current().nextFloat()<0.1f) return (ThreadLocalRandom.current().nextFloat()*2.0f-1.0f);
        return genValue;
    }


    void check(){
        System.out.print("\nneuronsWeightHidenOut\n");
        for (float[] neurons: neuronsWeightHiddenOut
        ) {
            for (float element: neurons
            ) {
                System.out.print(element+" ");
            }
            System.out.print("\n");
        }
        System.out.print("\nneuronsWeightHidenIn\n");
        for (float[][] birds: neuronsWeightHiddenIn
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
