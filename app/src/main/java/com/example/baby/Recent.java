package com.example.baby;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Recent extends AppCompatActivity {
    ListView listView;
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
        listView=(ListView)findViewById(R.id.recent);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        date = sdf.format(new Date());





        SessionManagement sessionManagement = new SessionManagement(this);
        child_id =  sessionManagement.getSessionChild();
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


    void recentDiaper ()
    {
        String changeTime,status;
        cursor = databaseHelper.recentAcitvityDiaper(date ,child_id);
        if (cursor.getCount() == 0 )
        {
            //nothing
        }
        else {
            cursor.moveToFirst();
            do {
                changeTime = cursor.getString(0);
                status = cursor.getString(2);
                list.add("Time:"+ changeTime +"Diaper Status"+ status);
                display(list);
            }while (cursor.moveToNext());
        }
    }

    void recentSleep ()
    {
        String start_Time,end_time, total;
        cursor = databaseHelper.recentActivitySleep(date ,child_id);
        if (cursor.getCount() == 0 )
        {
            //nothing
        }
        else {
            cursor.moveToFirst();
            do {
                start_Time = cursor.getString(0);
                end_time = cursor.getString(1);
                total = cursor.getString(2);
                list.add("Slept from "+start_Time +" to "+ end_time +" "+total+" hours");
                display(list);
            }while (cursor.moveToNext());
        }
    }

    void recentFeedingN ()
    {
    String time,left_time,right_time,total_time;
        cursor = databaseHelper.recentAcitvityFeedingN(date ,child_id);
        if (cursor.getCount() == 0 )
        {
            //nothing
        }
        else {
            cursor.moveToFirst();
            do {
                time = cursor.getString(0);
                left_time = cursor.getString(1);
                right_time = cursor.getString(2);
                total_time=cursor.getString(3);
                if (left_time.matches("00:00"))
                {
                    list.add(time +" Last Nursed using right side for "+total_time);
                    display(list);
                }
                else if (right_time.matches("00:00"))
                {
                    list.add(time +" Last Nursed using left side for "+total_time);
                    display(list);

                }
                else {
                    list.add(time + " Last Nursed using both sides for "+total_time);
                    display(list);
                }
            }while (cursor.moveToNext());
        }
    }

    void recentFeedingB ()
    {
        String time,type,quantity;
        cursor = databaseHelper.recentAcitvityFeedingB(date ,child_id);
        if (cursor.getCount() == 0 )
        {
            //nothing
        }
        else {
            cursor.moveToFirst();
            do {
                time = cursor.getString(0);
                quantity = cursor.getString(2);
                type = cursor.getString(1);
                list.add(time +" "+ type+ " " +quantity);
                display(list);
            }while (cursor.moveToNext());
        }
    }

    void recentFeedingS ()
    {
        String time,food,quantity;

        cursor = databaseHelper.recentAcitvityFeedingS(date ,child_id);
        if (cursor.getCount() == 0 )
        {
            //nothing
        }
        else {
            cursor.moveToFirst();
            do {
                time = cursor.getString(0);
                quantity = cursor.getString(1);
                food = cursor.getString(2);

                list.add(time +" "+ quantity+" "+food);
                display(list);
            }while (cursor.moveToNext());
        }
    }

    void recentMedication ()
    {
        String time, dosage, med_name;
        cursor = databaseHelper.recentAcitivty(date ,child_id);
        if (cursor.getCount() == 0 )
        {
            //nothing
        }
        else {
            cursor.moveToFirst();
            do {
                time = cursor.getString(0);
                dosage = cursor.getString(1);


                med_name = cursor.getString(2);
                list.add(time +" "+ dosage + " " +med_name);
                display(list);
            }while (cursor.moveToNext());
        }
    }

    void recentPumping ()
    {
        String time,totaltime,totalquantity;
        cursor = databaseHelper.recentAcitivtyPumping(date ,child_id);
        if (cursor.getCount() == 0 )
        {
            //nothing
        }
        else {
            cursor.moveToFirst();
            do {
                time = cursor.getString(0);
                totalquantity = cursor.getString(1);
                totaltime = cursor.getString(2);
                list.add(time +" "+ totalquantity +" "+ totaltime);
                display(list);
            }while (cursor.moveToNext());
        }
    }

    void recentNote ()
    {
        String time, text;
        cursor = databaseHelper.recentAcitivtyNotes(date ,child_id);
        if (cursor.getCount() == 0 )
        {
            //nothing
        }
        else {
            cursor.moveToFirst();
            do {
                time = cursor.getString(0);
                text = cursor.getString(1);
                list.add(time +" "+ text);
                display(list);
            }while (cursor.moveToNext());
        }
    }

    void display(List<String> listArray)
    {
        List<String> lI = listArray;

        if (lI.isEmpty())
        {
            Toast.makeText(this," qwertyuiopsdfghjxcvbn LI" +lI,Toast.LENGTH_SHORT).show();
        }
        else
        {
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, lI);listView.setAdapter(adapter);
        }

    }
}