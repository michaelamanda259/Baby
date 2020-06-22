package com.example.baby;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class ChildProfile extends AppCompatActivity {

    TextView name,gender,dob,bloodgroup;
    DatabaseHelper databaseHelper;
    SessionManagement sm;
    Cursor cursor;
    int child_id;
    String Name, Gender, DOB, BloodGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_profile);

        name = findViewById(R.id.tv_name);
        gender = findViewById(R.id.tv_gender);
        dob = findViewById(R.id.tv_dob);
        bloodgroup = findViewById(R.id.tv_bloodGroup);

        databaseHelper = new DatabaseHelper(this);
        SessionManagement sessionManagement = new SessionManagement(this);
        child_id= sessionManagement.getSessionChild();



        Cursor cursor = databaseHelper.childData( child_id);
        if (cursor.getCount() == 0 )
        {
            Toast.makeText(this,"Error .. ",Toast.LENGTH_SHORT).show();

        }
        else {
            cursor.moveToFirst();
            Name = cursor.getString(1);
            Gender = cursor.getString(2);
            DOB = cursor.getString(3);
            BloodGroup= cursor.getString(4);
        }
        name.setText(Name);
        gender.setText(Gender);
        dob.setText(DOB);
        bloodgroup.setText(BloodGroup);




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