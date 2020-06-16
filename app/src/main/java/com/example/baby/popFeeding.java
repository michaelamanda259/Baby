package com.example.baby;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

public class popFeeding extends Activity implements View.OnClickListener {
    private CardView cardViewBreastFeeding,cardViewBottleFeeding,cardViewSolidFeeding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_feeding);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.9),(int)(height*.6));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        //defining cards
        cardViewBreastFeeding = (CardView) findViewById(R.id.breastFeeding_card);
        cardViewBottleFeeding = (CardView) findViewById(R.id.bottleFeeding_card);
        cardViewSolidFeeding = (CardView) findViewById(R.id.solidFeeding_card);


        //adding click listener to cards
        cardViewBreastFeeding.setOnClickListener(this);
        cardViewBottleFeeding.setOnClickListener(this);
        cardViewSolidFeeding.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i ;

        switch (v.getId()) {
            case R.id.breastFeeding_card:
                i = new Intent(this, BreastFeedingActivity.class);
                startActivity(i);
                break;

            case R.id.bottleFeeding_card:
                i = new Intent(this, BottleFeedingActivity.class);
                startActivity(i);
                break;

            case R.id.solidFeeding_card:
                i = new Intent(this, SolidFeedingActivity.class);
                startActivity(i);
                break;

            default:
                break;
        }
    }
}