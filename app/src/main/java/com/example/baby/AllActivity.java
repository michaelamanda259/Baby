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

        recentFeedingN();
        recentFeedingB();
        recentFeedingS();
        recentPumping();
        recentSleep();
        recentDiaper();
        recentMedication();
        recentNote();


        recyclerView = findViewById(R.id.activity_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,list);
        recyclerView.setAdapter(adapter);

    }
    void recentDiaper() {
        String changeTime, status,date;
        cursor = databaseHelper.allActivityDiaper(child_id);
        if (cursor.getCount() == 0) {
            //nothing
        } else {
            cursor.moveToFirst();
            do {
                changeTime = cursor.getString(0);
                status = cursor.getString(2);
                date = cursor.getString(1);

                list.add(date+"\nDiaper:\n" +
                        "Time:" + changeTime + "\n" +
                        "Diaper Status: " + status);
            } while (cursor.moveToNext());
        }
    }

    void recentSleep() {
        String start_Time, end_time, total,date;
        cursor = databaseHelper.allActivitySleep(child_id);
        if (cursor.getCount() == 0) {
            //nothing
        } else {
            cursor.moveToFirst();
            do {
                start_Time = cursor.getString(0);
                end_time = cursor.getString(1);
                total = cursor.getString(2);
                date = cursor.getString(3);

                list.add(date+"\nSleep: \n" +
                        "Slept from " + start_Time + " to " + end_time + "\nTotal:" + total);
                } while (cursor.moveToNext());
        }
    }

    void recentFeedingN() {
        String date ,time, left_time, right_time, total_time;
        cursor = databaseHelper.allActivityFeedingN(child_id);
        if (cursor.getCount() == 0) {
            //nothing
        } else {
            cursor.moveToFirst();
            do {
                date = cursor.getString(4);
                time = cursor.getString(0);
                left_time = cursor.getString(1);
                right_time = cursor.getString(2);
                total_time = cursor.getString(3);
                if (left_time.matches("00:00")) {
                    list.add(date+"\nNursing: \n"+"Time :"+  time + "\n" +
                            " Last Nursed using right side for " + total_time);

                } else if (right_time.matches("00:00")) {
                    list.add(date+"\nNursing: \n"+"Time :"+  time + "\n" +
                            " Last Nursed using left side for " + total_time);


                } else {
                    list.add(date+"\nNursing: \n"+ "Time :"+ time + "\n" +
                            " Last Nursed using both sides for " + total_time);
                }
            } while (cursor.moveToNext());
        }
    }

    void recentFeedingB() {
        String time, type, quantity,date;
        cursor = databaseHelper.allActivityFeedingB(child_id);
        if (cursor.getCount() == 0) {
            //nothing
        } else {
            cursor.moveToFirst();
            do {
                time = cursor.getString(0);
                quantity = cursor.getString(2);
                type = cursor.getString(1);
                date = cursor.getString(3);

                list.add(date+"\nBottle Feeding: \n" +
                        "Time :"+ time + "\n Type: " + type + ":" + quantity+"ml");
            } while (cursor.moveToNext());
        }
    }

    void recentFeedingS() {
        String time, food, quantity,date;

        cursor = databaseHelper.allActivityFeedingS(child_id);
        if (cursor.getCount() == 0) {
            //nothing
        } else {
            cursor.moveToFirst();
            do {
                time = cursor.getString(0);
                quantity = cursor.getString(1);
                food = cursor.getString(2);
                date = cursor.getString(3);

                list.add(date+"\nSolid Feeding :\n" +
                        "Time :"+ time + "\n" + quantity + "g of:" + food);
            } while (cursor.moveToNext());
        }
    }

    void recentMedication() {
        String time, dosage, med_name,date;
        cursor = databaseHelper.allActivityMedication(child_id);
        if (cursor.getCount() == 0) {
            //nothing
        } else {
            cursor.moveToFirst();
            do {
                time = cursor.getString(0);
                dosage = cursor.getString(1);
                date = cursor.getString(3);



                med_name = cursor.getString(2);
                list.add(date+"\nMedication:\n" +
                        "Time :"+ time +  "\n" + med_name+" : " + dosage+" mg");
            } while (cursor.moveToNext());
        }
    }

    void recentPumping() {
        String time, totaltime, totalquantity,date;
        cursor = databaseHelper.allActivityPumping(child_id);
        if (cursor.getCount() == 0) {
            //nothing
        } else {
            cursor.moveToFirst();
            do {
                time = cursor.getString(0);
                totalquantity = cursor.getString(1);
                totaltime = cursor.getString(2);
                date = cursor.getString(3);

                list.add(date+"\nPumping:\n" +
                        "Time:"+time + "\n" + totalquantity + "ml\nTotal time:" + totaltime);            } while (cursor.moveToNext());
        }
    }

    void recentNote() {
        String time, text,date;
        cursor = databaseHelper.allActivityNotes(child_id);
        if (cursor.getCount() == 0) {
            //nothing
        } else {
            cursor.moveToFirst();
            do {
                time = cursor.getString(0);
                text = cursor.getString(1);
                date = cursor.getString(2);

                list.add(date+"\nTime:"+time + "\nNote:" + text);

            } while (cursor.moveToNext());
        }
    }

}