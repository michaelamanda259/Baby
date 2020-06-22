package com.example.baby;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DiaperActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
    ImageButton buttonBack,buttonSubmit;
    TimePickerDialog timePickerDialog;
    EditText editTextTime,editTextDate;
    DatabaseHelper databaseHelper;
    ChildModel cm;


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
        setContentView(R.layout.activity_diaper);


        editTextDate = findViewById(R.id.et_date);
        editTextTime = findViewById(R.id.et_time);
        radioGroup = findViewById(R.id.rg_diaperStatus);
        buttonBack = findViewById(R.id.cancel_button);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaperActivity.this,Dashboard.class);
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

                new DatePickerDialog(DiaperActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void chooseTime(View view) {

        Calendar calendar = Calendar.getInstance();
        int hourOfDay=calendar.get(Calendar.HOUR);
        int minute=calendar.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(DiaperActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

    private Boolean validateTime() {
        String val = editTextTime.getText().toString();

        if (val.isEmpty()) {
            editTextTime.setError("Field can not be empty");
            return false;
        } else {
            editTextTime.setError(null);
//            editTextDate.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateStaus(){
        int val = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(val);
        if(val==-1){
                return false;
        }
        else
            return true;
    }

    public void saveData(View view)
    {
        databaseHelper = new DatabaseHelper(DiaperActivity.this);
        String status ,date , time;
        int childid;
        boolean success = false;
        if(!validateDate() | !validateTime() | !validateStaus()  ){
            Toast.makeText(DiaperActivity.this,"Enter all details...", Toast.LENGTH_SHORT).show();

            return;
        }
        int val = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(val);
        status = (String) radioButton.getText();
        date = editTextDate.getText().toString();
        time = editTextTime.getText().toString();

        SessionManagement sessionManagement = new SessionManagement(DiaperActivity.this);
        childid =  sessionManagement.getSessionChild();

        try {
            success=databaseHelper.addDiaper(date,time,status,childid);
        } catch (Exception e) {
            databaseHelper.addDiaper("Error","Error","Error",0);
        }
        if (success)
        {
            Toast.makeText(DiaperActivity.this,"Added...?"+success, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DiaperActivity.this, Dashboard.class);
            startActivity(intent);

        }

        }




    }

