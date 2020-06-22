package com.example.baby;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.DatePickerDialog;

public class BottleFeedingActivity extends AppCompatActivity {
    EditText editTextTime,editTextMeasure,editTextDate;
    TimePickerDialog timePickerDialog;
    RadioGroup radioGroup;
    RadioButton radio;
    ImageButton buttonBack,buttonSubmit;
    DatabaseHelper databaseHelper;
    String selectedRadioButtonText;

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
        setContentView(R.layout.activity_bottle_feeding);

        editTextDate = findViewById(R.id.et_date);
        editTextTime = findViewById(R.id.et_time);
        editTextMeasure = findViewById(R.id.et_measure1);
        radioGroup = findViewById(R.id.rg_milkType);
        buttonBack = findViewById(R.id.cancel_button);
        buttonSubmit = findViewById(R.id.confirm_button);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BottleFeedingActivity.this,Dashboard.class);
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

                new DatePickerDialog(BottleFeedingActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

      //radio button

      radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(RadioGroup group, int checkedId) {
              RadioButton radioButton = (RadioButton)group.findViewById(checkedId);
          }
      });


    }
//time
    public void chooseTime(View view) {

        Calendar calendar = Calendar.getInstance();
        int hourOfDay=calendar.get(Calendar.HOUR);
        int minute=calendar.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(BottleFeedingActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

        if (val.isEmpty() ) {
            editTextTime.setError("Field can not be empty");
            return false;
        } else {
            editTextTime.setError(null);
            return true;
        }
    }

    private Boolean validateType(){
    int val = radioGroup.getCheckedRadioButtonId();
    if (val == -1){
        return false;
    }
    else{
        radio =  findViewById(val);
        return true;
    }
    }

    private Boolean validateQ(){
        String val2 = editTextMeasure.getText().toString();

        if (val2.isEmpty()  ) {
            editTextMeasure.setError("Field can not be empty");
            return false;
        } else {
            editTextMeasure.setError(null);
            return true;
        }
    }

    public void saveData(View view) {
        databaseHelper = new DatabaseHelper(this);
        String type, date, time;
        int childid, quantity;
        boolean success = false;
        if (!validateDate() | !validateTime() | !validateType() | !validateQ()) {
            Toast.makeText(this, "Enter all details...", Toast.LENGTH_SHORT).show();

            return;
        }
        date = editTextDate.getText().toString();
        time = editTextTime.getText().toString();
        type = radio.getText().toString();
        quantity = Integer.parseInt(editTextMeasure.getText().toString());
        SessionManagement sessionManagement = new SessionManagement(this);
        childid =  sessionManagement.getSessionChild();


        try {
            success=databaseHelper.addBottleFeeding(date,time,type,quantity,childid);
        } catch (Exception e) {
            databaseHelper.addBottleFeeding("Error","Error","Error",0,0);
        }
        if (success)
        {
            Toast.makeText(this,"Added...?"+success, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Dashboard.class);
            startActivity(intent);

        }
    }

}
