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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SleepActivity extends AppCompatActivity {

    EditText etTimeStart,etTimeEnd,etTimeTotal,editTextDate,editTextTime;
    TimePickerDialog timePickerDialog;
    ImageButton buttonBack,buttonSubmit;
    DatabaseHelper databaseHelper;


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

    private Boolean validateDate() {
        String val = editTextDate.getText().toString();

        if (val.isEmpty()) {
            editTextDate.setError("Field can not be empty");
            return false;
        } else {
            editTextDate.setError(null);
//            editTextDate.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateStartTime() {
        String val = etTimeStart.getText().toString();

        if (val.isEmpty() ) {
            etTimeStart.setError("Field can not be empty");
            return false;
        } else {
            etTimeStart.setError(null);
            return true;
        }
    }

    private Boolean validateEndTime(){
        String val2 = etTimeEnd.getText().toString();

        if (val2.isEmpty()  ) {
            etTimeEnd.setError("Field can not be empty");
            return false;
        } else {
            etTimeEnd.setError(null);
            return true;
        }
    }

    public void saveData(View view)
    {
        databaseHelper = new DatabaseHelper(SleepActivity.this);
        String status ,date , time;
        int childid;
        boolean success = false;
        if(!validateDate() | !validateStartTime() | !validateEndTime()  ){
            Toast.makeText(SleepActivity.this,"SELECT AN OPTION...", Toast.LENGTH_SHORT).show();

            return;
        }
/*
        int val = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(val);
        status = (String) radioButton.getText();
        date = editTextDate.getText().toString();
        time = editTextTime.getText().toString();
*/

    /*    SessionManagement sessionManagement = new SessionManagement(SleepActivity.this);
        childid =  sessionManagement.getSessionChild();
        Toast.makeText(SleepActivity.this," child_id..."+childid, Toast.LENGTH_SHORT).show();

        try {
            success=databaseHelper.addDiaper(date,time,status,childid);
            Toast.makeText(DiaperActivity.this,"Added", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            databaseHelper.addDiaper("Error","Error","Error",0);
        }
        if (success)
        {
            Toast.makeText(DiaperActivity.this,"Success..."+success, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DiaperActivity.this, Dashboard.class);
            startActivity(intent);

        }
*/
    }
}