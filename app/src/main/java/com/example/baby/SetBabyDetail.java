package com.example.baby;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class SetBabyDetail extends AppCompatActivity {

    TextInputLayout name, DOB, bloodgroup;
    RadioGroup gender ;
    ImageButton confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_baby_detail);

        name = findViewById(R.id.etName);
        DOB = findViewById(R.id.etAge);
        gender = findViewById(R.id.rg_gender);
        bloodgroup = findViewById(R.id.et_BloodGroup);

        confirm = findViewById(R.id.confirm_button);
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

/*
    private Boolean validateGender() {
        String val = Integer.toString(gender.getCheckedRadioButtonId());

        if (val.isEmpty()) {
            gender.setError("Field can not be empty");
            return false;
        } else {
            name.setError(null);
            name.setErrorEnabled(false);
            return true;
        }
    }
*/

    private Boolean validateBloodgroup() {
        String val = bloodgroup.getEditText().getText().toString();

        if (val.isEmpty()) {
            name.setError("Field can not be empty");
            return false;
        } else {
            name.setError(null);
            name.setErrorEnabled(false);
            return true;
        }


    }

    public void registerChild(View view) {

        if(!validateName() | !validateDOB() | !validateBloodgroup()){
            return;
        }
        String Name = name.getEditText().getText().toString();
        int DOBs =  Integer.parseInt(DOB.getEditText().getText().toString());
        String Gender = Integer.toString(gender.getCheckedRadioButtonId());
        String Bloodgroup = bloodgroup.getEditText().getText().toString();
        ChildModel childModel;

        try {
            childModel = new ChildModel(Name,Gender,Bloodgroup,DOBs);
            Toast.makeText(SetBabyDetail.this,"Registered", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            childModel = new ChildModel("Error","Error","Error",0);
        }

        DatabaseHelper databaseHelper  = new DatabaseHelper(SetBabyDetail.this);
        boolean success = databaseHelper.addChild(childModel);
        Toast.makeText(SetBabyDetail.this,"Success"+ success, Toast.LENGTH_SHORT).show();
    }



}