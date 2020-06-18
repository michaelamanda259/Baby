package com.example.baby;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.api.Backend;

public class SetBabyDetail extends AppCompatActivity {

    TextInputLayout name, DOB, bloodgroup;
    public RadioGroup gender ;
    ImageButton confirm;
    DatabaseHelper databaseHelper = new DatabaseHelper(SetBabyDetail.this);
    public int phone;
    TextView radio;
    RadioButton selectedRadioButton ;
    String selectedRadioButtonText;


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
            name.setError("Field can not be empty");
            return false;
        } else {
            name.setError(null);
            name.setErrorEnabled(false);
            return true;
        }


    }

    public void registerChild(View view) {

        if(!validateName() | /*!validateDOB() |*/ !validateBloodgroup() |  !validateGender()){
            Toast.makeText(SetBabyDetail.this,"Failed2", Toast.LENGTH_SHORT).show();

            return;}

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
        Toast.makeText(SetBabyDetail.this,"Successfully added ? "+ success, Toast.LENGTH_SHORT).show();
        if (success==true) {
            startActivity(new Intent(SetBabyDetail.this, Dashboard.class));

        }
    }


}