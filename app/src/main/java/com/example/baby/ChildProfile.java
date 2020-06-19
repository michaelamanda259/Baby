package com.example.baby;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ChildProfile extends AppCompatActivity {

    TextView name,gender,dob,bloodGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_profile);

        name = findViewById(R.id.tv_name);
        gender = findViewById(R.id.tv_gender);
        dob = findViewById(R.id.tv_dob);
        bloodGroup = findViewById(R.id.tv_bloodGroup);
    }

    public void backtobhome(View view) {
        Intent intent = new Intent(ChildProfile.this,Dashboard.class);
        startActivity(intent);
    }

    public void babyDetail(View view) {
        Intent intent = new Intent(ChildProfile.this,SetBabyDetail.class);
        startActivity(intent);
    }
}