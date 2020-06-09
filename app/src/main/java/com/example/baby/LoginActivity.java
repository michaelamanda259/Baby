package com.example.baby;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    Button callSignUp,login_btn;
    ImageView imageView;
    TextView logoText, sloganText;
    TextInputLayout username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        callSignUp = findViewById(R.id.btn_sign_up);
        imageView = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.tv_sign_in);
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        login_btn = findViewById(R.id.login_btn);

        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);

                Pair[] pairs = new Pair[7];

                pairs[0] = new Pair<View,String>(imageView, "logo_image");
                pairs[1] = new Pair<View,String>(logoText, "logo_text");
                pairs[2] = new Pair<View,String>(sloganText, "logo_text2");
                pairs[3] = new Pair<View,String>(username, "tan_text1");
                pairs[4] = new Pair<View,String>(password, "tan_forgetpass");
                pairs[5] = new Pair<View,String>(login_btn, "tan_go");
                pairs[6] = new Pair<View,String>(callSignUp, "tan_signup");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
                startActivity(intent, options.toBundle());
            }
        });
    }

    private Boolean validateUserName()
    {
        return true;
    }




}

