package com.example.baby;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class UserProfile extends AppCompatActivity {

    TextView username,fullname,phonenumber,email,name;
    String userName , name1, phone, email2;
    DatabaseHelper databaseHelper;
    SessionManagement sm;
    Cursor cursor1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        username = findViewById(R.id.tv_username);
        fullname = findViewById(R.id.tv_fullname);
        name=findViewById(R.id.tv_name);
        phonenumber = findViewById(R.id.tv_phoneNumber);
        email = findViewById(R.id.tv_email);

        databaseHelper = new DatabaseHelper(UserProfile.this);


        SessionManagement sessionManagement = new SessionManagement(this);

        String userN;
        userN= sessionManagement.getSession();
        int phone1 = databaseHelper.parentPhone(userN);


        Toast.makeText(this," "+phone1,Toast.LENGTH_SHORT).show();
        Toast.makeText(this," "+userN,Toast.LENGTH_SHORT).show();


        cursor1 = databaseHelper.userData(phone1);

        if (cursor1.getCount() == 0 )
        {
            Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
        }
        else {
            cursor1.moveToFirst();

            userName = cursor1.getString(0);
            name1 = cursor1.getString(1);
            email2 = cursor1.getString(2);
            phone= cursor1.getString(3);
        }

        username.setText(userName);
        fullname.setText(name1);
        name.setText(name1);
        phonenumber.setText(phone);
        email.setText(email2);



    }

    public void backtobhome(View view) {
        Intent intent = new Intent(UserProfile.this,Dashboard.class);
        startActivity(intent);
    }


}