package com.example.baby;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDB;

    private static int SPLASH_SCREEN = 4000;

    Animation topAnim,bottomAnim;
    ImageView imageView;
    TextView logo,slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Animation
        topAnim=AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim=AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Hooks
        imageView=findViewById(R.id.imageView);
        logo=findViewById(R.id.textView2);
        slogan=findViewById(R.id.textView3);

        imageView.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(findViewById(R.id.imageView),"logo_image");
                pairs[1] = new Pair<View,String>(findViewById(R.id.textView2),"logo_text");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                startActivity(intent,options.toBundle());
            }
        },SPLASH_SCREEN);

    }
}
