package com.example.baby;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    TextInputLayout name, userName, userPhoneNumber, userEmail, userPassword;
    Button regButton, loginButton;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    // Write a message to the database
   /* FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.etName);
        userName = findViewById(R.id.etUserName);
        userEmail = findViewById(R.id.etUserEmail);
        userPhoneNumber = findViewById(R.id.etPhoneNumber);
        userPassword = findViewById(R.id.etUserPassword);

/*
        regButton = findViewById(R.id.btReg);
*/
        loginButton = findViewById(R.id.btLogin);

        //firebaseAuth = FirebaseAuth.getInstance();



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
        String noWhiteSpace= "(?=\\s+$)";

        if (val.isEmpty()) {
            userName.setError("Field can not be empty");
            return false;
        }
        else if (val.length()>=15){
            userName.setError("Username too long");
            return false;
        }
        else if (!val.matches(noWhiteSpace))
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
        String emailPat="^[a-zA-Z0-9_+&*-]+(?:\\\\.\"+ \n" +
                        "\"[a-zA-Z0-9_+&*-]+)*@\" + \n" +
                        "\"(?:[a-zA-Z0-9-]+\\\\.)+[a-z\" + \n" +
                        "\"A-Z]{2,7}$";


        if (val.isEmpty()) {
            userEmail.setError("Field can not be empty");
            return false;
        }
        else if (!val.matches(emailPat))
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

        if (val.isEmpty()) {
            userPhoneNumber.setError("Field can not be empty");
            return false;
        } else {
            userPhoneNumber.setError(null);
            userPhoneNumber.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = userPassword.getEditText().getText().toString();
        String paawordVal = "^(?=.*[0-9])" +
                            "(?=.*[a-z])" +
                            "(?=.*[A-Z])" +
                            "(?=.*[@#$%^&+=])" +
                            "(?=\\s+$)." +
                            "{8,}$";

/*        Explanation:

^                 # start-of-string
                (?=.*[0-9])       # a digit must occur at least once
                (?=.*[a-z])       # a lower case letter must occur at least once
                (?=.*[A-Z])       # an upper case letter must occur at least once
                (?=.*[@#$%^&+=])  # a special character must occur at least once
                (?=\S+$)          # no whitespace allowed in the entire string
                .{8,}             # anything, at least eight places though
        $                 # end-of-string*/

        if (val.isEmpty()) {
            userPassword.setError("Field can not be empty");
            return false;
        }
        else if (!val.matches(paawordVal))
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

        if (! validateName() | !validateUserName()  | !validateEmail() | !validatePassword() | !validatePhoneNumber()) {

            return;
        }
            String Name = name.getEditText().getText().toString();
            String UserName = userName.getEditText().getText().toString();
            String email = userEmail.getEditText().getText().toString();
            String phone = userPhoneNumber.getEditText().getText().toString();
            String password = userPassword.getEditText().getText().toString();

            UserHelperClass helperClass = new UserHelperClass(Name, UserName, email, phone, password);

            reference.child(phone).setValue(helperClass);


    }

}