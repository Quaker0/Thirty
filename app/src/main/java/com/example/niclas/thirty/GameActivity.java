package com.example.niclas.thirty;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * VieWerActivity communicates with strings.xml and drawable directory as well as Views.
 */
public class GameActivity extends AppCompatActivity {
    private final Model model = new Model();
    private static final String TAG = "QuizActivity";
    private static final String KEY_RESULT = "result";
    private static final String KEY_DICES = "dices";
    private static final String KEY_THROWS = "throws";
    private static final String KEY_COLORS = "colors";
    private final CalculateHelper calc = new CalculateHelper(this,model);
    private Button throwButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thirty);
        throwButton = (Button) findViewById(R.id.throw_button);
        //Press throw button
        throwButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throwButton();
            }
        });

        model.setDice1 ((ImageView) findViewById(R.id.dice1));
        model.setDice2 ((ImageView)  findViewById(R.id.dice2));
        model.setDice3 ((ImageView)  findViewById(R.id.dice3));
        model.setDice4 ((ImageView)  findViewById(R.id.dice4));
        model.setDice5 ((ImageView)  findViewById(R.id.dice5));
        model.setDice6 ((ImageView)  findViewById(R.id.dice6));

        //Select dices
        final ImageView[] dices = model.getDices();
        for (int i=0;i<dices.length;i++) {
            final int j = i;
            dices[j].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(model.getThrowCount()>0) {
                        model.reverseDiceColor(j);
                        int oldRes = model.getDiceResult(j);
                        selectDice(dices[j].getId(), model.getDiceColor(j) + oldRes);
                    }
                }
            });
        }

        Spinner dropdown = (Spinner)findViewById(R.id.number_spinner);
        String[] items = model.SPINNER_ITEMS;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dropdown.setAdapter(adapter);

        if (savedInstanceState != null) {
            int throwCount = savedInstanceState.getInt(KEY_THROWS,0);
            int[] diceResults = savedInstanceState.getIntArray(KEY_DICES);
            ArrayList<Integer> results = savedInstanceState.getIntegerArrayList(KEY_RESULT);
            String[] colors = savedInstanceState.getStringArray(KEY_COLORS);
            model.setThrowCount(throwCount);
            model.setDiceResults(diceResults);
            model.setResults(results);
            model.setDiceColors(colors);
            setDicesFromSave();
            setInstruction();
        } else {
            setInstruction("Begin by throwing the dices!");
        }
    }

    /**
     * Overload function, for setting a source image in an ImageView
     * @param imageId
     * @param newImageName
     */
    public void setImage(String imageId, String newImageName) {
        int resID = getResources().getIdentifier(imageId, "id", getPackageName());
        setImage(resID, newImageName);
    }

    /**
     * Restrieve saved dice state from model.
     */
    public void setDicesFromSave() {
        int[] dices = model.getDiceResult();
        for(int diceIndex=1; diceIndex<=6; diceIndex++) {
            String imageId = model.DICE + diceIndex;
            String newImageName = model.getDiceColor(diceIndex-1) + dices[diceIndex-1];
            setImage(imageId, newImageName);
        }
    }


    /**
     * Define what happens when pressing the "throw" button.
     */
    public void throwButton() {
        model.incrementThrowCount();
        if (model.getThrowCount() <= 3) {
            for (int i = 1; i < 7; i++) {
                if (model.getDiceColor(i - 1) == model.WHITE_DICE || model.getThrowCount()==1) {
                    int diceNum = Model.throwDice();
                    model.addDiceResult(i - 1, diceNum);
                    setDice(i, diceNum);
                    model.setDiceColorWhite(i-1);
                }
            }
        }
        if(model.getThrowCount() == 3) {
            setThrowButtonTextAlt();
        } else if (model.getThrowCount() > 3){
            showResults(calc.calculateResult());
            String throwBtnText = getThrowButtonTextStd();
            throwButton.setText(throwBtnText);
            model.resetThrowCount();
            resetDices();
        }
        setInstruction();
    }

    /**
     * Set the dice to a specific number.
     * @param diceIndex
     * @param diceNum
     */
    public void setDice(int diceIndex, int diceNum) {
        String imageId = model.DICE + diceIndex;
        String newImageName = model.WHITE_DICE + diceNum;
        setImage(imageId, newImageName);
    }

    /**
     * Set the text for the "Throw" button.
     * @param text
     */
    public void setThrowButtonText(String text) {
        throwButton.setText(text);
        throwButton.setHighlightColor(Color.GREEN);
    }

    /**
     * Set the standard text for the Throw-button.
     */
    public void setThrowButtonText() {
        throwButton.setText(getThrowButtonTextStd());
        throwButton.setHighlightColor(Color.GREEN);
    }

    /**
     * Returns standard Throw-button name.
     * @return
     */
    public String getThrowButtonTextStd() {
        return getResources().getString(R.string.throw_button);
    }

    /**
     * Abstraction, specifies what function is used for selecting dice.
     * @param diceId
     * @param imageName
     */
    public void selectDice(int diceId, String imageName) {
        setImage(diceId, imageName);
    }

    /**
     * Set a source image in an ImageView
     * @param imageId
     * @param newImageName
     */
    private void setImage(int imageId, String newImageName) {
        ImageView img = (ImageView) findViewById(imageId);
        int imgID = getResources().getIdentifier(newImageName, "drawable", getPackageName());
        img.setImageResource(imgID);
    }

    /**
     * Set the user instruction.
     * @param text
     */
    public void setInstruction(String text) {
        TextView textView = (TextView)findViewById(R.id.instruction);
        textView.setText(text);
    }

    /**
     * Show result in new activity.
     * @param res
     */
    public void showResults(ArrayList<Integer> res) {
        Intent intent = Result.getIntent(GameActivity.this,res);
        startActivity(intent);
    }

    /**
     * Get selected spinner value.
     * @return
     */
    public String getSpinnerValue() {
        Spinner mySpinner = (Spinner) findViewById(R.id.number_spinner);
        return mySpinner.getSelectedItem().toString();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putIntegerArrayList(KEY_RESULT, model.getResults());
        savedInstanceState.putIntArray(KEY_DICES, model.getDiceResult());
        savedInstanceState.putInt(KEY_THROWS, model.getThrowCount());
        savedInstanceState.putStringArray(KEY_COLORS, model.getDiceColors());
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Resets all dices to white nr1.
     */
    public void resetDices() {
        for (int i = 1; i <= 6; i++) {
            String imageId = model.DICE + i;
            String newImageName = model.WHITE_DICE+1;
            setImage(imageId, newImageName);
        }
        model.resetDiceResult();
    }

    /**
     * Change the text of "Throw button" to the alt text.
     */
    public void setThrowButtonTextAlt() {
        setThrowButtonText(getResources().getString(R.string.throw_button_alt));
    }

    /**
     * Standard instruction
     */
    public void setInstruction() {
        int throwCount = model.getThrowCount();
        if (throwCount>=3) {
            setInstruction("Select the multiples to be counted in the spinner in the lower left.");
        } else if(throwCount <= 0) {
            setInstruction("Begin by throwing the dices!");
        } else {
            setInstruction("Select the dices you want to keep!\n"
                    + Math.max(0, 3 - throwCount) + " throws left.");
        }
    }

}
