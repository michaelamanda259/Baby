    package com.example.baby;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import android.app.ActivityOptions;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.net.Uri;
    import android.os.Bundle;
    import android.util.Pair;
    import android.view.View;
    import android.view.WindowManager;
    import android.widget.Button;
    import android.widget.CheckBox;
    import android.widget.CompoundButton;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;
    import com.google.android.material.textfield.TextInputLayout;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.Query;
    import com.google.firebase.database.ValueEventListener;

    public class LoginActivity extends AppCompatActivity {

        Button callSignUp,login_btn;
        ImageView imageView;
        TextView logoText, sloganText;
        TextInputLayout username,password;
        CheckBox remember;
        public String checkbox;

        DatabaseHelper db  = new DatabaseHelper(LoginActivity.this);


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
            remember = findViewById(R.id.cb_remember);

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

                   startActivity(intent);
                }
            });
        }

        private Boolean validateUsername() {
            String val = username.getEditText().getText().toString();
            if (val.isEmpty()) {
                username.setError("Field cannot be empty");
                return false;
            } else {
                username.setError(null);
                username.setErrorEnabled(false);
                return true;
            }
        }

        private Boolean validatePassword() {
            String val = password.getEditText().getText().toString();
            if (val.isEmpty()) {
                password.setError("Field cannot be empty");
                return false;
            } else {
                password.setError(null);
                password.setErrorEnabled(false);
                return true;
            }
        }

        public void loginUser(View view) {
            //Validate Login Info
            if (!validateUsername() | !validatePassword()) {
                return;
            } else {
                isUser();
            }
        }

        private void isUser() {
            if (remember.isChecked()) checkbox = "true";
            else checkbox ="false";

            final String userEnteredUsername = username.getEditText().getText().toString().trim();
            final String userEnteredPassword = password.getEditText().getText().toString().trim();
            ParentModel parentModel = new ParentModel(userEnteredUsername);

            if (db.login(userEnteredUsername, userEnteredPassword))
            {
                SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
                String username;
                if ( checkbox.equals("true"))
                {
                    sessionManagement.saveSession(parentModel);
                    username = sessionManagement.getSession();
                    if (username.matches("ERROR")) {
                        Toast.makeText(LoginActivity.this, " AN ERROR OCCURRED... " + sessionManagement, Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        sessionManagement.saveSession(parentModel);
                        Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                        startActivity(intent);
                    }
                }
                 else{
                    Intent intent = new Intent(LoginActivity.this,Dashboard.class);
                    startActivity(intent);
                }
            }
            else
            {
                Toast.makeText(LoginActivity.this,"Login Failed... Try again !",Toast.LENGTH_LONG).show();
            }
        }

        public void forgetPassword(View view) {
            Intent intent = new Intent(LoginActivity.this,Dashboard.class);
            startActivity(intent);
        }
    }

