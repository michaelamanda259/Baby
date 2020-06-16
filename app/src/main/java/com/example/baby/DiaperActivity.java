package com.example.baby;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class DiaperActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
    ImageButton buttonBack,buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diaper);

        radioGroup = findViewById(R.id.rg_diaperStatus);
        buttonBack = findViewById(R.id.cancel_button);
        buttonSubmit = findViewById(R.id.confirm_button);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaperActivity.this,Dashboard.class);
                startActivity(intent);
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton)findViewById(selectId);
                Toast.makeText(DiaperActivity.this,radioButton.getText(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
