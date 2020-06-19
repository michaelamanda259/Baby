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
        String status ;
        int date , time, id , childid;
        if(!validateDate() | !validateTime() | !validateStaus()  ){
            Toast.makeText(DiaperActivity.this,"SELECT AN OPTION...", Toast.LENGTH_SHORT).show();

            return;
        }
        ;
        int val = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(val);
        status = (String) radioButton.getText();
        SessionManagement sessionManagement = new SessionManagement(DiaperActivity.this);
        childid =  sessionManagement.getSessionChild();


        Toast.makeText(DiaperActivity.this,"Status..."+status, Toast.LENGTH_SHORT).show();
        Toast.makeText(DiaperActivity.this,"Childid..."+childid, Toast.LENGTH_SHORT).show();



      /*  Intent intent = new Intent(DiaperActivity.this,Dashboard.class);
        startActivity(intent);



        String Name = name.getEditText().getText().toString();
        int DOBs =  Integer.parseInt(DOB.getEditText().getText().toString());
        String Gender = selectedRadioButtonText;
        SessionManagement sessionManagement = new SessionManagement(SetBabyDetail.this);

        String userName;
        userName= sessionManagement.getSession();
        phone = databaseHelper.parentPhone(userName);
        int Phone = phone;

        String Bloodgroup = bloodgroup.getEditText().getText().toString();
        ChildModel childModel;

        try {
            childModel = new ChildModel(Name,Gender,DOBs,Bloodgroup,Phone);
            Toast.makeText(SetBabyDetail.this,"Registered", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            childModel = new ChildModel("Error","Error",0,"Error",0);
        }

        boolean success = databaseHelper.addChild(childModel);
        Toast.makeText(SetBabyDetail.this,"Successfully added ? "+ success, Toast.LENGTH_SHORT).show();        */


        }




    }

