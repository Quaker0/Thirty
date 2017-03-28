package com.example.niclas.thirty;

import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Niclas on 2017-01-24.
 */

public class Model{
    public static final String WHITE_DICE = "white_dice";
    public static final String GREY_DICE = "grey_dice";
    public static final String DICE = "dice";
    private ImageView mDice1;
    private ImageView mDice2;
    private ImageView mDice3;
    private ImageView mDice4;
    private ImageView mDice5;
    private ImageView mDice6;
    private int mThrowCount;
    private ArrayList<Integer> mAllResults = new ArrayList<Integer>();
    private ArrayList<Integer> mTurnResults = new ArrayList<Integer>();
    private int[] mDiceResults = new int[] {1,1,1,
                                            1,1,1};
    private String[] mDiceColors = new String[]    {WHITE_DICE,WHITE_DICE,WHITE_DICE,
                                                    WHITE_DICE,WHITE_DICE,WHITE_DICE};
    final static String[] SPINNER_ITEMS = new String[]{"Low", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

    /**
     * Get color of all the dices in an array
     * @return
     */
    public String[] getDiceColors() {
        return mDiceColors;
    }

    /**
     * Set color of dice
     * @param diceColors
     */
    public void setDiceColors(String[] diceColors) {
        this.mDiceColors = diceColors;
    }

    /**
     * Set Dice results
     * @param diceResults
     */
    public void setDiceResults(int[] diceResults) {
        this.mDiceResults = diceResults;
    }

    /**
     * Set throw count
     * @param throwCount
     */
    public void setThrowCount(int throwCount) {
        this.mThrowCount = throwCount;
    }

    /**
     * Set results
     * @param results
     */
    public void setResults(ArrayList<Integer> results) {
        mTurnResults = results;
    }

    /**
     * Get results
     * @return
     */
    public ArrayList<Integer> getResults() {
        return mAllResults;
    }

    /**
     * Get turnresults
     * @return
     */
    public ArrayList<Integer> getTurnResults() {
        return mTurnResults;
    }

    /**
     * Reset turn results
     */
    public void resetTurnResults() {
        mTurnResults = new ArrayList<Integer>();
    }

    /**
     * Reset all results
     */
    public void resetAllResults() {
        mTurnResults = new ArrayList<Integer>();
    }


    /**
     * Add results at end of round
     * @param result
     */
    public void addTurnResult(int result) {
        mTurnResults.add(result);
    }

    /**
     * Add results at end of round after calculation of all all turn results
     * @param result
     */
    public void addResult(int result) {
        mAllResults.add(result);
    }

    /**
     * Get array of results
     * @return
     */
    public int[] getDiceResult() {
        return mDiceResults;
    }

    /**
     * Get result nr i, i has to be in interval [0,5]. Else indexoutofbound exception.
     * @param i
     * @return
     */
    public int getDiceResult(int i) {
        return mDiceResults[i];
    }

    /**
     * Add results at end of turn
     * @param pos
     * @param res
     */
    public void addDiceResult(int pos, int res) {
        mDiceResults[pos] = res;
    }

    /**
     * Reset dice results
     */
    public void resetDiceResult() {
        mDiceResults = new int[] {1,1,1,1,1,1};
    }

    public void setDiceColorWhite(int i) {
        mDiceColors[i]=WHITE_DICE;
    }

    /**
     * Reverse color of dice i
     * @param i
     */
    public void reverseDiceColor(int i) {
        if(mDiceColors[i]==WHITE_DICE) {
            mDiceColors[i]=GREY_DICE;
        } else {
            mDiceColors[i]=WHITE_DICE;
        }
    }

    /**
     * Get color of dice i
     * @param i
     * @return
     */
    public String getDiceColor(int i) {
        return mDiceColors[i];
    }

    /**
     * Reset dices
     */
    public void clearDiceColors() {
        for (int i = 0; i< mDiceColors.length; i++)
        {
               mDiceColors[i] = WHITE_DICE;
        }
    }

    /**
     * Get throw count
     * @return
     */
    public int getThrowCount() {
        return mThrowCount;
    }

    /**
     * Increment throw count at each round
     */
    public void incrementThrowCount() {
        this.mThrowCount++;
    }

    /**
     * Reset throw count at roundcompletion
     */
    public void resetThrowCount() {
        this.mThrowCount = 0;
    }


    /**
     * Get pointers to all dices
     * @return
     */
    public ImageView[] getDices() {
        return new ImageView[]{mDice1,mDice2,mDice3,mDice4,mDice5,mDice6};
    }

    /**
     * Get dice at position 1, upper left.
     * @return
     */
    public ImageView getDice1() {
        return mDice1;
    }

    /**
     * Set dice at position 1, upper left.
     * @param dice1
     */
    public void setDice1(ImageView dice1) {
        mDice1 = dice1;
    }

    /**
     * Get dice at position 2, upper middle.
     * @return
     */
    public ImageView getDice2() {
        return mDice2;
    }

    /**
     * Set dice at position 2, upper middle.
     * @param dice2
     */
    public void setDice2(ImageView dice2) {
        mDice2 = dice2;
    }

    /**
     * Get dice at position 3, upper right.
     * @return
     */
    public ImageView getDice3() {
        return mDice3;
    }

    /**
     * Set dice at position 3, upper right.
     * @param dice3
     */
    public void setDice3(ImageView dice3) {
        mDice3 = dice3;
    }

    /**
     * Get dice at position 4, lower left.
     * @return
     */
    public ImageView getDice4() {
        return mDice4;
    }

    /**
     * Set dice at position 4, lower left.
     * @param dice4
     */
    public void setDice4(ImageView dice4) {
        mDice4 = dice4;
    }

    /**
     * Get dice at position 5, lower middle.
     * @return
     */
    public ImageView getDice5() {
        return mDice5;
    }

    /**
     * Set dice at position 5, lower middle.
     * @param dice5
     */
    public void setDice5(ImageView dice5) {
        mDice5 = dice5;
    }

    /**
     * Get dice at position 6, lower right.
     * @return
     */
    public ImageView getDice6() {
        return mDice6;
    }

    /**
     * Set dice at position 6, lower right.
     * @param dice6
     */
    public void setDice6(ImageView dice6) {
        mDice6 = dice6;
    }

    /**
     * Returns an int in the interval [1,6]
     * @return
     */
    public static int throwDice() {
        Random rand = new Random();
        int randomNum = rand.nextInt(6)+1;
        return randomNum;
    }

    /**
     * Converts an int[] -> List<Integer>
     * @param arr
     * @return
     */
    public List<Integer> arrayToList(int[] arr) {
        List<Integer> intList = new ArrayList<Integer>();
        for (int d:arr)
        {
            intList.add(d);
        }
        return intList;
    }

}
