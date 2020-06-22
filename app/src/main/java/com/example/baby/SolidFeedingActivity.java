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
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SolidFeedingActivity extends AppCompatActivity {
    ImageButton buttonBack, buttonSubmit;
    DatabaseHelper databaseHelper;

    TimePickerDialog timePickerDialog;
    EditText editTextTime, editTextDate, editTextfood_content, editTextQuantity;

    public String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        System.out.println(c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());

        return formattedDate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solid_feeding);

        editTextDate = findViewById(R.id.et_date);
        editTextTime = findViewById(R.id.et_time);
        editTextfood_content = findViewById(R.id.et_content);
        editTextQuantity =findViewById(R.id.et_measure1);

        buttonBack = findViewById(R.id.cancel_button);
        buttonSubmit = findViewById(R.id.confirm_button);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SolidFeedingActivity.this, Dashboard.class);
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

                new DatePickerDialog(SolidFeedingActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void chooseTime(View view) {

        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(SolidFeedingActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                String am_pm;
                if (hourOfDay < 12) {
                    am_pm = "AM";
                    editTextTime.setText(hourOfDay + ":" + minute + " " + am_pm);
                } else if (hourOfDay == 12) {
                    am_pm = "PM";
                    editTextTime.setText(hourOfDay + ":" + minute + " " + am_pm);

                } else {
                    am_pm = "PM";
                    editTextTime.setText(hourOfDay + ":" + minute + " " + am_pm);

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

    private Boolean validateTime() {
        String val = editTextTime.getText().toString();

        if (val.isEmpty() ) {
            editTextTime.setError("Field can not be empty");
            return false;
        } else {
            editTextTime.setError(null);
            return true;
        }
    }

    private Boolean validateContent(){
        String val2 = editTextfood_content.getText().toString();

        if (val2.isEmpty()  ) {
            editTextfood_content.setError("Field can not be empty");
            return false;
        } else {
            editTextfood_content.setError(null);
            return true;
        }
    }

    private Boolean validateQ(){
        String val2 = editTextQuantity.getText().toString();

        if (val2.isEmpty()  ) {
            editTextQuantity.setError("Field can not be empty");
            return false;
        } else {
            editTextQuantity.setError(null);
            return true;
        }
    }

    public void saveData(View view) {
        databaseHelper = new DatabaseHelper(this);
        String food_content, date, time;
        int childid, quantity;
        boolean success = false;
        if (!validateDate() | !validateTime() | !validateContent() | !validateQ()) {
            Toast.makeText(this, "Enter all details...", Toast.LENGTH_SHORT).show();

            return;
        }
        date = editTextDate.getText().toString();
        time = editTextTime.getText().toString();
        food_content = editTextfood_content.getText().toString();
        quantity = Integer.parseInt(editTextQuantity.getText().toString());
        SessionManagement sessionManagement = new SessionManagement(this);
        childid =  sessionManagement.getSessionChild();
        try {
            success=databaseHelper.addSolidFeeding(date,time,food_content,quantity,childid);
        } catch (Exception e) {
            databaseHelper.addSolidFeeding("Error","Error","Error",0,0);
        }
        if (success)
        {
            Toast.makeText(this,"Added...?"+success, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Dashboard.class);
            startActivity(intent);

        }
    }

}
