package com.example.baby;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;


public class BreastFeedingActivity extends AppCompatActivity {
    Chronometer chronometer_l, chronometer_r, chronometer_t;
    private long pauseOffSetLeft;
    private long pauseOffSetRight;
    private long pauseOffSetTotal;

    EditText editTextTime;
    TimePickerDialog timePickerDialog;



    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breast_feeding);

        chronometer_l = findViewById(R.id.chronometer_left);
        chronometer_r = findViewById(R.id.chronometer_right);
        chronometer_t = findViewById(R.id.chronometer_total);
        editTextTime = findViewById(R.id.et_time);


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

    public void chooseTime(View view) {
        Calendar calendar = Calendar.getInstance();
        int hourOfDay=calendar.get(Calendar.HOUR);
        int minute=calendar.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(BreastFeedingActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                String am_pm;
                if (hourOfDay<12) {
                    am_pm = "AM";
                    editTextTime.setText(hourOfDay + ":" + minute + " " + am_pm);
                }
                else if (hourOfDay == 12){
                    am_pm = "PM";
                    editTextTime.setText(hourOfDay + ":" + minute + " " + am_pm);

                }
                else {
                    am_pm = "PM";
                    editTextTime.setText(hourOfDay + ":" + minute + " " + am_pm);

                }
            }
        },hourOfDay,minute,false);
        timePickerDialog.show();

    }
}