package com.example.baby;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.api.Backend;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class SetBabyDetail extends AppCompatActivity {

    TextInputLayout name, DOB, bloodgroup;
    public RadioGroup gender ;
    ImageButton confirm;
    DatabaseHelper databaseHelper = new DatabaseHelper(SetBabyDetail.this);
    public int phone;
    TextView radio;
    RadioButton selectedRadioButton ;
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
        setContentView(R.layout.activity_set_baby_detail);

        name = findViewById(R.id.etName);
        DOB = findViewById(R.id.etAge);
        gender = findViewById(R.id.rg_gender);
        bloodgroup = findViewById(R.id.et_BloodGroup);
        radio=findViewById(R.id.radiotext);
        confirm = findViewById(R.id.confirm_button);

        DOB.getEditText().setText(getCurrentDate());
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

                DOB.getEditText().setText(sdf.format(myCalendar.getTime()));
            }
        };

        DOB.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(SetBabyDetail.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }

    private Boolean validateName() {
        String val = name.getEditText().getText().toString();
        if (val.isEmpty()) {
            name.setError("Field can not be empty");
            return false;
        } else {
            name.setError(null);
            name.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateDOB() {
        String val = DOB.getEditText().getText().toString();

        if (val.isEmpty()) {
            DOB.setError("Field can not be empty");
            return false;
        } else {
            DOB.setError(null);
            DOB.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateGender() {
        int val = gender.getCheckedRadioButtonId();
        if (val == -1) {
            radio.setVisibility(View.VISIBLE);
            radio.setText("Nothing selected");
            return false;
        }
        else{

            selectedRadioButton =  findViewById(val);
            selectedRadioButtonText = selectedRadioButton.getText().toString();
            return true;
        }
    }

    private Boolean validateBloodgroup() {
        String val = bloodgroup.getEditText().getText().toString();

        if (val.isEmpty()) {
            bloodgroup.setError("Field can not be empty");
            return false;
        } else {
            bloodgroup.setError(null);
            bloodgroup.setErrorEnabled(false);
            return true;
        }


    }

    public void registerChild(View view) {

        if(!validateName() | !validateDOB() | !validateBloodgroup() |  !validateGender()){
            Toast.makeText(SetBabyDetail.this,"Failed", Toast.LENGTH_SHORT).show();

            return;}

        String Name = name.getEditText().getText().toString();
        String DOBs =  DOB.getEditText().getText().toString();
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
            childModel = new ChildModel("Error","Error","Error","Error",0);
        }

        boolean success = databaseHelper.addChild(childModel);
        Toast.makeText(SetBabyDetail.this,"Successfully added ? "+ success, Toast.LENGTH_SHORT).show();
        if (success==true) {
            SessionManagement sessionManagement1 = new SessionManagement(SetBabyDetail.this);
            int id;
            sessionManagement1.saveChildid(childModel);
            id = sessionManagement1.getSessionChild();
            if (id <= 0) {
                Toast.makeText(SetBabyDetail.this, " AN ERROR OCCURRED... " + sessionManagement1, Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(SetBabyDetail.this, " saved... " + sessionManagement1.getSessionChild(), Toast.LENGTH_LONG).show();

            }


            startActivity(new Intent(SetBabyDetail.this, LoginActivity.class));

        }
    }


}