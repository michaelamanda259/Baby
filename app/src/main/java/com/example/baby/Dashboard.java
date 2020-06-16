package com.example.baby;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class  Dashboard extends AppCompatActivity implements View.OnClickListener {
    private CardView cardViewFeeding,cardViewDiaper,cardViewPumping,cardViewSleep,cardViewMedication,cardViewNotes;
    Button logout,profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //defining cards
        cardViewFeeding = (CardView) findViewById(R.id.feeding_card);
        cardViewDiaper = (CardView) findViewById(R.id.diaper_card);
        cardViewPumping = (CardView) findViewById(R.id.pumping_card);
        cardViewSleep = (CardView) findViewById(R.id.sleeping_card);
        cardViewMedication = (CardView) findViewById(R.id.medication_card);
        cardViewNotes = (CardView) findViewById(R.id.notes_card);

        logout = findViewById(R.id.btn_logout);
        profile = findViewById(R.id.btn_profile);


        //adding click listener to cards
        cardViewFeeding.setOnClickListener(this);
        cardViewDiaper.setOnClickListener(this);
        cardViewPumping.setOnClickListener(this);
        cardViewSleep.setOnClickListener(this);
        cardViewMedication.setOnClickListener(this);
        cardViewNotes.setOnClickListener(this);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember","false");
                editor.apply();

                finish();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,SetBabyDetail.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {

        Intent i ;

        switch (v.getId()) {
            case R.id.feeding_card:
                i = new Intent(this, popFeeding.class);
                startActivity(i);
                break;

            case R.id.diaper_card:
                i = new Intent(this,DiaperActivity.class);
                startActivity(i);
                break;

            case R.id.pumping_card:
                i = new Intent(this,PumpingActivity.class);
                startActivity(i);
                break;

            case R.id.sleeping_card:
                i = new Intent(this,SleepActivity.class);
                startActivity(i);
                break;

            case R.id.medication_card:
                i = new Intent(this,MedicationActivity.class);
                startActivity(i);
                break;

            case R.id.notes_card:
                i = new Intent(this,NotesActivity.class);
                startActivity(i);
                break;

            default:break;

        }
    }
}
