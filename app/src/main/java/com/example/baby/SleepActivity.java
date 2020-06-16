package com.example.baby;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SleepActivity extends AppCompatActivity {

    EditText etTimeStart;
    EditText etTimeEnd;
    EditText etTimeTotal;
    TimePickerDialog timePickerDialog;
    ImageButton buttonBack,buttonSubmit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        etTimeStart = findViewById(R.id.et_time_start);
        etTimeEnd = findViewById(R.id.et_time_end);
        etTimeTotal = findViewById(R.id.et_time_total);

        buttonBack = findViewById(R.id.cancel_button);
        buttonSubmit = findViewById(R.id.confirm_button);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SleepActivity.this,Dashboard.class);
                startActivity(intent);
            }
        });

    }

    public void chooseStart(View view) {

        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(SleepActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                String am_pm;
                if (hourOfDay < 12) {
                    am_pm = "AM";
                    etTimeStart.setText(hourOfDay + ":" + minute + " " + am_pm);
                } else if (hourOfDay == 12) {
                    am_pm = "PM";
                    etTimeStart.setText(hourOfDay + ":" + minute + " " + am_pm);

                } else {
                    am_pm = "PM";
                    etTimeStart.setText(hourOfDay + ":" + minute + " " + am_pm);

                }
            }
        }, hourOfDay, minute, false);
        timePickerDialog.show();
    }

    public void chooseEnd(View view) {

        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(SleepActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                String am_pm;
                if (hourOfDay < 12) {
                    am_pm = "AM";
                    etTimeEnd.setText(hourOfDay + ":" + minute + " " + am_pm);
                } else if (hourOfDay == 12) {
                    am_pm = "PM";
                    etTimeEnd.setText(hourOfDay + ":" + minute + " " + am_pm);

                } else {
                    am_pm = "PM";
                    etTimeEnd.setText(hourOfDay + ":" + minute + " " + am_pm);

                }
            }
        }, hourOfDay, minute, false);
        timePickerDialog.show();
    }
}