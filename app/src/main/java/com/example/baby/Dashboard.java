package com.example.baby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.navigation.NavigationView;

public class  Dashboard extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private CardView cardViewFeeding,cardViewDiaper,cardViewPumping,cardViewSleep,cardViewMedication,cardViewNotes;
    Button logout,profile;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;



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

       /* logout = findViewById(R.id.btn_logout);
        profile = findViewById(R.id.btn_profile);*/

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        //toolbar
        setSupportActionBar(toolbar);

        //navigation drawer



        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


        //adding click listener to cards
        cardViewFeeding.setOnClickListener(this);
        cardViewDiaper.setOnClickListener(this);
        cardViewPumping.setOnClickListener(this);
        cardViewSleep.setOnClickListener(this);
        cardViewMedication.setOnClickListener(this);
        cardViewNotes.setOnClickListener(this);


        /*logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

       /* profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,SetBabyDetail.class);
                startActivity(intent);
            }
        });*/
    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        /*Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_Logout).setVisible(false);
        menu.findItem(R.id.nav_profile).setVisible(false);*/

        /*Intent intent;*/
        switch (menuItem.getItemId()) {
            case R.id.nav_profile:
               Intent intent = new Intent(Dashboard.this,UserProfile.class);
                startActivity(intent);
                break;

            case R.id.nav_babyprofile:
               Intent i = new Intent(Dashboard.this,ChildProfile.class);
                startActivity(i);
                break;



            case R.id.nav_Logout:
                SessionManagement sessionManagement = new SessionManagement(Dashboard.this);
                sessionManagement.removeSession();
                Intent in = new Intent(Dashboard.this,LoginActivity.class);
                startActivity(in);
                finish();

                break;


           /* case R.id.nav_login: menu.findItem(R.id.nav_Logout).setVisible(true);
                menu.findItem(R.id.nav_profile).setVisible(true);
                menu.findItem(R.id.nav_login).setVisible(false);
                break;
            case R.id.nav_Logout: menu.findItem(R.id.nav_Logout).setVisible(false);
                menu.findItem(R.id.nav_profile).setVisible(false);
                menu.findItem(R.id.nav_login).setVisible(true);
                break;*/
            case R.id.nav_share: Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show(); break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
