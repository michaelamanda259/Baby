package com.example.baby;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class BreastFeedingActivity extends AppCompatActivity {
    Chronometer chronometer_l, chronometer_r, chronometer_t;
    private long pauseOffSetLeft;
    private long pauseOffSetRight;
    private long pauseOffSetTotal;

    EditText editTextTime,editTextDate;
    TimePickerDialog timePickerDialog;
    ImageButton buttonBack,buttonSubmit;



    private boolean running;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breast_feeding);

        chronometer_l = findViewById(R.id.chronometer_left);
        chronometer_r = findViewById(R.id.chronometer_right);
        chronometer_t = findViewById(R.id.chronometer_total);
        editTextTime = findViewById(R.id.et_time);
        editTextDate = findViewById(R.id.et_date);
        buttonBack = findViewById(R.id.cancel_button);
        buttonSubmit = findViewById(R.id.confirm_button);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BreastFeedingActivity.this,Dashboard.class);
                startActivity(intent);
            }
        });


        //Calendar
        editTextDate.setText(getCurrentDate());
        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, dayOfMonth);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
            private void updateLabel() {
                String myFormat = "dd-MMM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                editTextDate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(BreastFeedingActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


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
    public String getCurrentDate(){
        Calendar c = Calendar.getInstance();
        System.out.println(c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());


        return formattedDate;
    }
}