package com.example.baby;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class admin extends AppCompatActivity {
    private static String text;
    DatabaseHelper databaseHelper;
    Cursor cursor;
    ArrayList<String> list = new ArrayList<>();
    ListView listView;
    String time;
    String text1;
    String text2;
    String text3;
    String text4;
    String a[];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        databaseHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.user);
        viewUsers();


    }

    void viewUsers()
    {
        cursor = databaseHelper.viewUsers();
        if (cursor.getCount() == 0) {
            //nothing
        } else {
            cursor.moveToFirst();
            do {
                text1 = cursor.getString(0);
                text2 = cursor.getString(1);
                text3 = cursor.getString(2);
                text4 = cursor.getString(3);
                list.add("User:" + text1 + " "+ text2+ " "+ text3+ " "+ text4);
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
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String selectedItem = (String) parent.getItemAtPosition(position);
                    a = selectedItem.split(" ");
                    Intent intent = new Intent(admin.this,compDetail.class);
                    intent.putExtra("text",a[3]);
                    startActivity(intent);
                }



            });
        }

    }


}