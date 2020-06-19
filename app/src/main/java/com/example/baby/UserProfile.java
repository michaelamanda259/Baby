package com.example.baby;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {

    TextView username,fullname,phonenumber,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        username = findViewById(R.id.tv_username);
        fullname = findViewById(R.id.tv_name);
        phonenumber = findViewById(R.id.tv_phoneNumber);
        email = findViewById(R.id.tv_email);

        username.setText("Amanda");
    }


    public void backtobhome(View view) {
        Intent intent = new Intent(UserProfile.this,Dashboard.class);
        startActivity(intent);
    }
}