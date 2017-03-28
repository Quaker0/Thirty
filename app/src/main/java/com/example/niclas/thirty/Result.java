package com.example.niclas.thirty;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Seperate window that shows results
 */
public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Bundle b = getIntent().getExtras();
        ArrayList<Integer> value = new ArrayList<Integer>();
        if(b != null)
            value = b.getIntegerArrayList("res");

        Button conButton = (Button) findViewById(R.id.continue_button);
        ArrayList<TableRow> rows = new ArrayList<TableRow>();
        rows.add((TableRow) findViewById(R.id.result_row2));
        rows.add((TableRow) findViewById(R.id.result_row3));
        rows.add((TableRow) findViewById(R.id.result_row4));
        rows.add((TableRow) findViewById(R.id.result_row5));
        rows.add((TableRow) findViewById(R.id.result_row6));
        rows.add((TableRow) findViewById(R.id.result_row7));
        rows.add((TableRow) findViewById(R.id.result_row8));
        rows.add((TableRow) findViewById(R.id.result_row9));
        rows.add((TableRow) findViewById(R.id.result_row10));

        TableRow resRowTot = (TableRow) findViewById(R.id.result_row);
        resRowTot.setVisibility(INVISIBLE);
        
        ArrayList<TextView> results = new ArrayList<TextView>();
        results.add((TextView) findViewById(R.id.result1));
        results.add((TextView) findViewById(R.id.result2));
        results.add((TextView) findViewById(R.id.result3));
        results.add((TextView) findViewById(R.id.result4));
        results.add((TextView) findViewById(R.id.result5));
        results.add((TextView) findViewById(R.id.result6));
        results.add((TextView) findViewById(R.id.result7));
        results.add((TextView) findViewById(R.id.result8));
        results.add((TextView) findViewById(R.id.result9));
        results.add((TextView) findViewById(R.id.result10));

        TextView resTot = (TextView) findViewById(R.id.result);
        ProgressBar mProgress = (ProgressBar) findViewById(R.id.progressBar);

        // Show the same amount of rows as their are results.

        if(value!=null) {
            for (int i = 0; i < 10; i++) {
                if (value.size() > i && value.get(i) != null) {
                    Log.d("res value",String.valueOf(value.get(i)));
                    results.get(i).setText(String.valueOf(value.get(i)));
                    mProgress.setProgress(i+1);
                    if (i == 9) { //All values
                        resRowTot.setVisibility(VISIBLE);
                        resTot.setText(String.valueOf(add(value)));
                    }
                } else {
                    rows.get(i-1).setVisibility(INVISIBLE);
                }
            }
        }
/*
        if(value != null && value.size() > 0) {
            Log.d("Res: ",String.valueOf(value.get(0)));
            res1.setText(String.valueOf(value.get(0)));
        }
        if(value != null && value.size() < 2) {
            resRow2.setVisibility(INVISIBLE);
            resRow3.setVisibility(INVISIBLE);
            resRowTot.setVisibility(INVISIBLE);
            mProgress.setProgress(1);
        } else if(value != null && value.size() < 3) {
            res2.setText(String.valueOf(value.get(1)));
            resRow3.setVisibility(INVISIBLE);
            mProgress.setProgress(2);
        } else if (value != null){
            res2.setText(String.valueOf(value.get(1)));
            res3.setText(String.valueOf(value.get(2)));
            resTot.setText(String.valueOf(value.get(0)+value.get(1)+value.get(2)));
            resRow2.setVisibility(VISIBLE);
            resRow3.setVisibility(VISIBLE);
            resRowTot.setVisibility(VISIBLE);
            mProgress.setProgress(3);
        }
*/
        // Close window
        conButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private int add(ArrayList<Integer> arr) {
        int res = 0;
        for (int i : arr) {
            res+=i;
        }
        return res;
    }

    /**
     * Get intent to start as new window
     * @param context
     * @param res
     * @return
     */
    public static Intent getIntent(Context context, ArrayList<Integer> res) {
        Intent intent = new Intent(context, Result.class);
        Bundle b = new Bundle();
        b.putIntegerArrayList("res",res);
        intent.putExtras(b);
        return intent;
    }
}
