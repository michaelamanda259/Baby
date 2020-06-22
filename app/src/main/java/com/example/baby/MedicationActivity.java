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

public class MedicationActivity extends AppCompatActivity {

    ImageButton buttonBack,buttonSubmit;
    TimePickerDialog timePickerDialog;
    EditText editTextTime,editTextDate, editName , editQ;
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
        setContentView(R.layout.activity_medication);

        editTextDate = findViewById(R.id.et_date);
        editTextTime = findViewById(R.id.et_time);
        editName = findViewById(R.id.et_nameofM);
        editQ = findViewById(R.id.et_quantityofM);
        buttonBack = findViewById(R.id.cancel_button);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicationActivity.this,Dashboard.class);
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

                new DatePickerDialog(MedicationActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void chooseTime(View view) {

        Calendar calendar = Calendar.getInstance();
        int hourOfDay=calendar.get(Calendar.HOUR);
        int minute=calendar.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(MedicationActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

    private Boolean validateName(){
        String val = editName.getText().toString();

        if (val.isEmpty()) {
            editName.setError("Field can not be empty");
            return false;
        } else {
            editName.setError(null);
//            editTextDate.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateQ(){
        String val = editQ.getText().toString();

        if (val.isEmpty()) {
            editQ.setError("Field can not be empty");
            return false;
        } else {
            editQ.setError(null);
            return true;
        }
    }


    public void saveData(View view)
    {
        databaseHelper = new DatabaseHelper(MedicationActivity.this);
        String medName, date , time;
        int childid, quantity;
        boolean success = false;
        if(!validateDate() | !validateTime() | !validateName() | !validateQ()  ){
            Toast.makeText(MedicationActivity.this,"Enter all details", Toast.LENGTH_SHORT).show();
            return;
        }
        date = editTextDate.getText().toString();
        time = editTextTime.getText().toString();
        medName = editName.getText().toString();
        quantity = Integer.parseInt(editQ.getText().toString());

        SessionManagement sessionManagement = new SessionManagement(MedicationActivity.this);
        childid =  sessionManagement.getSessionChild();

        try {
            success=databaseHelper.addMedName(date,time,medName,quantity,childid);
        } catch (Exception e) {
            databaseHelper.addMedName("Error","Error","Error",0,0);
        }
        if (success)
        {
            Toast.makeText(MedicationActivity.this,"Added...?"+success, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MedicationActivity.this, Dashboard.class);
            startActivity(intent);

        }

        /*

    */

    }
}
