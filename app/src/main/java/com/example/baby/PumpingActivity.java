package com.example.baby;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;

public class PumpingActivity extends AppCompatActivity {
    //Amount
    EditText editTextLeft,editTextRight,editTextTotal;

    //Chronometer
    Chronometer chronometer_l, chronometer_r, chronometer_t;
    private long pauseOffSetLeft;
    private long pauseOffSetRight;
    private long pauseOffSetTotal;

    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pumping);


        editTextLeft = findViewById(R.id.et_measure_left);
        editTextRight = findViewById(R.id.et_measure_right);
        editTextTotal = findViewById(R.id.et_measure_total);

        chronometer_l = findViewById(R.id.chronometer_left);
        chronometer_r = findViewById(R.id.chronometer_right);
        chronometer_t = findViewById(R.id.chronometer_total);


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!editTextLeft.getText().toString().equals("")&& !editTextRight.getText().toString().equals("")){
                    int temp1 = Integer.parseInt(editTextLeft.getText().toString());
                    int temp2 = Integer.parseInt(editTextRight.getText().toString());
                    editTextTotal.setText(String.valueOf(temp1+temp2));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        };
        editTextLeft.addTextChangedListener(textWatcher);
        editTextRight.addTextChangedListener(textWatcher);

    }



    public void leftStartChronometer (View view){
        if (!running){
            chronometer_l.setBase(SystemClock.elapsedRealtime() - pauseOffSetLeft);
            chronometer_l.start();

            chronometer_t.setBase(SystemClock.elapsedRealtime() - pauseOffSetTotal);
            chronometer_t.start();
            running = true;
        }
    }

    public void leftPauseChronometer (View view){
        if (running){
            chronometer_l.stop();
            pauseOffSetLeft = SystemClock.elapsedRealtime() - chronometer_l.getBase();

            chronometer_t.stop();
            pauseOffSetTotal = SystemClock.elapsedRealtime() - chronometer_t.getBase();

            running=false;
        }

    }

    public void leftStopChronometer (View view){

            /*
            chronometer_t.setBase(chronometer_t.getBase() - pauseOffSetLeft);
*/
        if (running){
            chronometer_l.setBase(SystemClock.elapsedRealtime());
            pauseOffSetLeft = 0;
            chronometer_l.stop();

            chronometer_t.stop();
            pauseOffSetTotal = SystemClock.elapsedRealtime() - chronometer_t.getBase();

            running = false;
        }

    }

    public void rightStartChronometer (View view){
        if (!running){
            chronometer_r.setBase(SystemClock.elapsedRealtime() - pauseOffSetRight);
            chronometer_r.start();

            chronometer_t.setBase(SystemClock.elapsedRealtime() - pauseOffSetTotal);
            chronometer_t.start();
            running = true;
        }
    }

    public void rightPauseChronometer (View view){
        if (running){
            chronometer_r.stop();
            pauseOffSetRight = SystemClock.elapsedRealtime() - chronometer_r.getBase();

            chronometer_t.stop();
            pauseOffSetTotal = SystemClock.elapsedRealtime() - chronometer_t.getBase();
            running=false;
        }
    }

    public void rightStopChronometer (View view){

        if (running){

            chronometer_r.setBase(SystemClock.elapsedRealtime());
            pauseOffSetRight = 0;
            chronometer_r.stop();

            chronometer_t.stop();
            pauseOffSetTotal = SystemClock.elapsedRealtime() - chronometer_t.getBase();

            running = false;

        }

    }

    public void resetTotal(View view) {
        chronometer_t.setBase(SystemClock.elapsedRealtime());
        pauseOffSetTotal = 0;

        chronometer_r.setBase(SystemClock.elapsedRealtime());
        pauseOffSetRight = 0;

        chronometer_l.setBase(SystemClock.elapsedRealtime());
        pauseOffSetLeft = 0;

        running = false;
    }

}