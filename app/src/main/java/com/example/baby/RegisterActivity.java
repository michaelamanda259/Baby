package com.example.baby;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    TextInputLayout name, userName, userPhoneNumber, userEmail, userPassword;
    Button regButton, loginButton;
    DatabaseHelper databaseHelper;
    ParentModel parentModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.etName);
        userName = findViewById(R.id.etUserName);
        userEmail = findViewById(R.id.etUserEmail);
        userPhoneNumber = findViewById(R.id.etPhoneNumber);
        userPassword = findViewById(R.id.etUserPassword);
        loginButton = findViewById(R.id.btLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
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

    private Boolean validateUserName() {
        String val = userName.getEditText().getText().toString();
        String noWhiteSpace= "[^\\s-]";

        if (val.isEmpty()) {
            userName.setError("Field can not be empty");
            return false;
        }
        else if (val.length()>=15){
            userName.setError("Username too long");
            return false;
        }
        else if (val.contains(" "))
        {
            userName.setError("Username can not contain space");
            return false;
        }
        else {
            userName.setError(null);
            userName.setErrorEnabled(false);
            return true;
        }


    }

    private Boolean validateEmail() {
        String val = userEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        if (val.isEmpty()) {
            userEmail.setError("Field can not be empty");
            return false;
        }
        else if (!val.matches(emailPattern))
        {
            userEmail.setError("Invalid email address");
            return false;
        }

        else {
            userEmail.setError(null);
            userEmail.setErrorEnabled(false);
            return true;
        }


    }

    private Boolean validatePhoneNumber() {
        String val = userPhoneNumber.getEditText().getText().toString();
        long phone1 = Long.parseLong(userPhoneNumber.getEditText().getText().toString());
        if (val.isEmpty()) {
            userPhoneNumber.setError("Field can not be empty");
            return false;
        }
        else if(val.length()>10){
            userPhoneNumber.setError("Number invaild.. too long");
            return false;
        }
        else
            {
            userPhoneNumber.setError(null);
            userPhoneNumber.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = userPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";


        if (val.isEmpty()) {
            userPassword.setError("Field can not be empty");
            return false;
        }
        else if (!val.matches(passwordVal))
        {
            userPassword.setError("Password is too weak");
            return false;
        }
        else {
            userPassword.setError(null);
            userPassword.setErrorEnabled(false);
            return true;
        }


    }

    public void registerUser(View view) {

        if(!validateName() | !validatePassword() | !validatePhoneNumber() | !validateEmail() | !validateUserName()){
            return;
        }
        String Name = name.getEditText().getText().toString();
        String UserName = userName.getEditText().getText().toString();
        String email = userEmail.getEditText().getText().toString();
        long phone = Long.parseLong(userPhoneNumber.getEditText().getText().toString());
        String password = userPassword.getEditText().getText().toString();
        ParentModel parentModel;

        try {
            parentModel = new ParentModel(Name,UserName,email,phone,password);
        } catch (Exception e) {
            parentModel = new ParentModel("Error","Error","Error",0,"Error");
        }

        DatabaseHelper databaseHelper  = new DatabaseHelper(RegisterActivity.this);
        SessionManagement sessionManagement = new SessionManagement(this);

        boolean success = databaseHelper.addParent(parentModel);
        Toast.makeText(RegisterActivity.this,"Registered Successfully"+ success, Toast.LENGTH_SHORT).show();
        if (success )
        {
            sessionManagement.saveSession(parentModel);
            Intent intent = new Intent(RegisterActivity.this, SetBabyDetail.class);
            startActivity(intent);

        }

    }

}