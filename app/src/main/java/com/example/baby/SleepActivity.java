package com.example.baby;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SleepActivity extends AppCompatActivity {

    EditText etTimeStart,etTimeEnd,etTimeTotal,editTextTime,editTextDate;;
    TimePickerDialog timePickerDialog;
    ImageButton buttonBack,buttonSubmit;


    public String getCurrentDate(){
        Calendar c = Calendar.getInstance();
        System.out.println(c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());

        return formattedDate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        editTextDate = findViewById(R.id.et_date);
        editTextTime = findViewById(R.id.et_time);

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

                new DatePickerDialog(SleepActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!etTimeStart.getText().toString().equals("") && !etTimeEnd.getText().toString().equals("")) {

                    SimpleDateFormat format = new SimpleDateFormat("HH:mm");

                    String dateStart = etTimeStart.getText().toString();
                    String dateStop = etTimeEnd.getText().toString();


                    try {
                        Date d1 = format.parse(dateStart);
                        Date d2 = format.parse(dateStop);

                        //in milliseconds
                        long diff = d2.getTime() - d1.getTime();
//                        long diffHours = diff / (60 * 60 * 1000) % 24;
                        long days = (int) (diff / (1000*60*60*24));
                        long hours = (int) ((diff - (1000*60*60*24*days)) / (1000*60*60));
                        long min = (int) (diff - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
                        hours = (hours < 0 ? -hours : hours);
                        etTimeTotal.setText(hours + " hours ");
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        etTimeStart.addTextChangedListener(textWatcher);
        etTimeEnd.addTextChangedListener(textWatcher);

    }
    public void chooseTime(View view) {

        Calendar calendar = Calendar.getInstance();
        int hourOfDay=calendar.get(Calendar.HOUR);
        int minute=calendar.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(SleepActivity.this, new TimePickerDialog.OnTimeSetListener() {
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