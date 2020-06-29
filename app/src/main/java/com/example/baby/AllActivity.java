package com.example.baby;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AllActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<String> list;

    Cursor cursor;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    String date;
    int child_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);

        list=new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        date = sdf.format(new Date());
        SessionManagement sessionManagement = new SessionManagement(this);
        child_id = sessionManagement.getSessionChild();
        recentNote();
        recentDiaper();
        recentSleep();
        recentFeedingN();
        recentFeedingB();
        recentFeedingS();
        recentMedication();
        recentPumping();


        recyclerView = findViewById(R.id.activity_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,list);
        recyclerView.setAdapter(adapter);

    }
    void recentDiaper() {
        String changeTime, status;
        cursor = databaseHelper.allActivityDiaper(child_id);
        if (cursor.getCount() == 0) {
            //nothing
        } else {
            cursor.moveToFirst();
            do {
                changeTime = cursor.getString(0);
                status = cursor.getString(2);
                list.add("Time:" + changeTime + "\n" +
                        "Diaper Status" + status);
            } while (cursor.moveToNext());
        }
    }

    void recentSleep() {
        String start_Time, end_time, total;
        cursor = databaseHelper.recentActivitySleep(date, child_id);
        if (cursor.getCount() == 0) {
            //nothing
        } else {
            cursor.moveToFirst();
            do {
                start_Time = cursor.getString(0);
                end_time = cursor.getString(1);
                total = cursor.getString(2);
                list.add("Slept from " + start_Time + " to " + end_time + " " + total + " hours");
            } while (cursor.moveToNext());
        }
    }

    void recentFeedingN() {
        String time, left_time, right_time, total_time;
        cursor = databaseHelper.recentActivityFeedingN(date, child_id);
        if (cursor.getCount() == 0) {
            //nothing
        } else {
            cursor.moveToFirst();
            do {
                time = cursor.getString(0);
                left_time = cursor.getString(1);
                right_time = cursor.getString(2);
                total_time = cursor.getString(3);
                if (left_time.matches("00:00")) {
                    list.add(time + " Last Nursed using right side for " + total_time);

                } else if (right_time.matches("00:00")) {
                    list.add(time + " Last Nursed using left side for " + total_time);


                } else {
                    list.add(time + " Last Nursed using both sides for " + total_time);

                }
            } while (cursor.moveToNext());
        }
    }

    void recentFeedingB() {
        String time, type, quantity;
        cursor = databaseHelper.recentActivityFeedingB(date, child_id);
        if (cursor.getCount() == 0) {
            //nothing
        } else {
            cursor.moveToFirst();
            do {
                time = cursor.getString(0);
                quantity = cursor.getString(2);
                type = cursor.getString(1);
                list.add(time + " " + type + " " + quantity);

            } while (cursor.moveToNext());
        }
    }

    void recentFeedingS() {
        String time, food, quantity;

        cursor = databaseHelper.recentActivityFeedingS(date, child_id);
        if (cursor.getCount() == 0) {
            //nothing
        } else {
            cursor.moveToFirst();
            do {
                time = cursor.getString(0);
                quantity = cursor.getString(1);
                food = cursor.getString(2);

                list.add(time + " " + quantity + " " + food);

            } while (cursor.moveToNext());
        }
    }

    void recentMedication() {
        String time, dosage, med_name;
        cursor = databaseHelper.recentActivity(date, child_id);
        if (cursor.getCount() == 0) {
            //nothing
        } else {
            cursor.moveToFirst();
            do {
                time = cursor.getString(0);
                dosage = cursor.getString(1);


                med_name = cursor.getString(2);
                list.add(time + " " + dosage + " " + med_name);

            } while (cursor.moveToNext());
        }
    }

    void recentPumping() {
        String time, totaltime, totalquantity;
        cursor = databaseHelper.recentActivityPumping(date, child_id);
        if (cursor.getCount() == 0) {
            //nothing
        } else {
            cursor.moveToFirst();
            do {
                time = cursor.getString(0);
                totalquantity = cursor.getString(1);
                totaltime = cursor.getString(2);
                list.add(time + " " + totalquantity + " " + totaltime);
            } while (cursor.moveToNext());
        }
    }

    void recentNote() {
        String time, text;
        cursor = databaseHelper.allActivityNotes(child_id);
        if (cursor.getCount() == 0) {
            //nothing
        } else {
            cursor.moveToFirst();
            do {
                time = cursor.getString(0);
                text = cursor.getString(1);
                list.add(time + " " + text);

            } while (cursor.moveToNext());
        }
    }

}