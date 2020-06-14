package com.example.baby;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class DiaperActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
    ImageButton btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diaper);

        radioGroup = findViewById(R.id.rg_diaperStatus);
        btnConfirm = findViewById(R.id.confirm_button);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton)findViewById(selectId);
                Toast.makeText(DiaperActivity.this,radioButton.getText(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
