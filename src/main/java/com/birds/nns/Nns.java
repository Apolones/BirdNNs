package com.birds.nns;

import java.util.concurrent.ThreadLocalRandom;

public class Nns {
    public final int numbersOfBird = 50;
    public final int numbersOfNeurons = 6;
    public final int numbersOfParameters = 4;

    public int generation;
    public long bestScore;

    private float[][] neuronsIn = new float[numbersOfBird][numbersOfParameters];                         //1-number bird 2-data bird ( 0-hight 1-hole 2-accseleration 3-speed)
    private float[][][] neuronsWeightHiddenIn = new float[numbersOfBird][numbersOfParameters +1][numbersOfNeurons];         //1-number birds 2 weight neuronsIn 3 valueHidenNeurons
    private float[][] neuronsValueHidden = new float[numbersOfBird][numbersOfNeurons];
    private float[][] neuronsWeightHiddenOut = new float[numbersOfBird][numbersOfNeurons+1];             //1-number birds 2 weight neuronsOut
    private float[][] bestBirdWeightHiddenIn = new float[numbersOfParameters +1][numbersOfNeurons];
    private float[] bestBirdWeightHiddenOut = new float[numbersOfNeurons+1];
    private float[][] deltaBirdWeightHiddenIn = new float[numbersOfParameters +1][numbersOfNeurons];
    private float[] deltaBirdWeightHiddenOut = new float[numbersOfNeurons+1];

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
        if (exitValue>0.3f)return true;

        return false;
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
