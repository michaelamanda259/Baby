package com.example.baby;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Recent extends AppCompatActivity {
    ListView listView;
    Adapter adapter;
    ImageButton buttonBack;
    Cursor cursor;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    String date;
    int child_id;
    ArrayList<String> list = new ArrayList<>();
    SimpleDateFormat sdf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);
        databaseHelper = new DatabaseHelper(this);
        buttonBack = findViewById(R.id.cancel_button);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Recent.this, Dashboard.class);
                startActivity(intent);
            }
        });

        listView = findViewById(R.id.recent);
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
        display(list);
    }


    void recentDiaper() {
        String changeTime, status;
        cursor = databaseHelper.recentActivityDiaper(date, child_id);
        if (cursor.getCount() == 0) {
            //nothing
        } else {
            cursor.moveToFirst();
            do {
                changeTime = cursor.getString(0);
                status = cursor.getString(2);
                list.add("Diaper:\n" +
                        "Time:" + changeTime + "\n" +
                        "Diaper Status: " + status);
                display(list);
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
                list.add("Sleep: \n" +
                        "Slept from " + start_Time + " to " + end_time + "\nTotal:" + total);
                display(list);
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
                    list.add("Nursing: \n"+"Time :"+  time + "\n" +
                            " Last Nursed using right side for " + total_time);
                    display(list);
                } else if (right_time.matches("00:00")) {
                    list.add("Nursing: \n"+"Time :"+  time + "\n" +
                            " Last Nursed using left side for " + total_time);
                    display(list);

                } else {
                    list.add("Nursing: \n"+ "Time :"+ time + "\n" +
                            " Last Nursed using both sides for " + total_time);
                    display(list);
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
                list.add("Bottle Feeding: \n" +
                        "Time :"+ time + "\n Type: " + type + ":" + quantity+"ml");
                display(list);
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

                list.add("Solid Feeding :\n" +
                        "Time :"+ time + "\n" + quantity + "g of:" + food);
                display(list);
            } while (cursor.moveToNext());
        }
    }

    void recentMedication() {
        String time, dosage, med_name;
        cursor = databaseHelper.recentActivityMedication(date, child_id);
        if (cursor.getCount() == 0) {
            //nothing
        } else {
            cursor.moveToFirst();
            do {
                time = cursor.getString(0);
                dosage = cursor.getString(1);


                med_name = cursor.getString(2);
                list.add("Medication:\n" +
                        "Time :"+ time +  "\n" + med_name+" : " + dosage+" mg");
                display(list);
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
                list.add("Pumping:\n" +
                        "Time:"+time + "\n" + totalquantity + "ml\nTotal time:" + totaltime);
                display(list);
            } while (cursor.moveToNext());
        }
    }

    void recentNote() {
        String time, text;
        cursor = databaseHelper.recentActivityNotes(date, child_id);
        if (cursor.getCount() == 0) {
            //nothing
        } else {
            cursor.moveToFirst();
            do {
                time = cursor.getString(0);
                text = cursor.getString(1);
                list.add("Time:"+time + "\nNote:" + text);
                display(list);
            } while (cursor.moveToNext());
        }
    }

    void display(List<String> listArray) {
        List<String> lI = listArray;

        if (lI.isEmpty()) {
            Toast.makeText(this, " No activities yet..." , Toast.LENGTH_SHORT).show();
        } else {
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, lI);
            listView.setAdapter(adapter);
        }

    }
}