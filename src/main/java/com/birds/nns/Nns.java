package com.birds.nns;

import java.util.concurrent.ThreadLocalRandom;

public class Nns {
    public final int numbersOfBird = 15;
    private final int numbersOfNeurons = 6;
    private final int numbersOfParameters = 4;

    public int generation;
    public long bestScore;

    private final float[][] neuronsIn = new float[numbersOfBird][numbersOfParameters];                                         //1-number bird 2-data bird ( 0-distanceTopPipeY 1-distancePipeX 2-distanceBotPipeY 3-speed)
    private final float[][][] neuronsWeightHiddenIn = new float[numbersOfBird][numbersOfParameters +1][numbersOfNeurons];
    private final float[][] neuronsValueHidden = new float[numbersOfBird][numbersOfNeurons];
    private final float[][] neuronsWeightHiddenOut = new float[numbersOfBird][numbersOfNeurons+1];
    private final float[][] bestBirdWeightHiddenIn = new float[numbersOfParameters +1][numbersOfNeurons];
    private final float[] bestBirdWeightHiddenOut = new float[numbersOfNeurons+1];

    public Nns() {
        //generate random Weight neurons (-1,1)
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
        generation=1;
        bestScore=0;
    }

    public void updateValueBird(int numberBird, double distanceX, double distanceTopY, double distanceBotY, double speed ) {
        //all values must be normalized
        neuronsIn[numberBird][0]= (float) distanceX;
        neuronsIn[numberBird][1]= (float) distanceTopY;
        neuronsIn[numberBird][2]= (float) distanceBotY;
        neuronsIn[numberBird][3]= (float) speed;
    }

    public boolean isJump(int numberBird){

        for(int i=0;i<numbersOfNeurons; i++){
            neuronsValueHidden[numberBird][i]=0;
            for(int j = 0; j< numbersOfParameters; j++){
                neuronsValueHidden[numberBird][i]+=neuronsIn[numberBird][j]* neuronsWeightHiddenIn[numberBird][j][i];
            }
            neuronsValueHidden[numberBird][i]+=neuronsWeightHiddenIn[numberBird][numbersOfParameters][i];
            neuronsValueHidden[numberBird][i]= activationFunction(neuronsValueHidden[numberBird][i]);
        }
        float exitValue=0.0f;

        for(int i=0;i<numbersOfNeurons;i++){
            exitValue+= neuronsValueHidden[numberBird][i]* neuronsWeightHiddenOut[numberBird][i];
        }
        exitValue+= neuronsWeightHiddenOut[numberBird][numbersOfNeurons];
        exitValue=activationFunction(exitValue);
        return exitValue > 0.5f;
    }

    private float activationFunction(float value) {

        //Sigmoid
        return (float) (1/(1+Math.exp(-value)));

        //ReLU
        //if(value>0)return value; else return 0;
    }

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



}
