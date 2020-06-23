package com.example.baby;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class Recent extends AppCompatActivity {
    ListView listView;
    Cursor cursor;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    String changeTime,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);
        databaseHelper = new DatabaseHelper(this);
        listView=(ListView)findViewById(R.id.recent);
        recentD();
    }
    void recentD ()
    {
        cursor = databaseHelper.recentAcitivty();
        if (cursor.getCount() == 0 )
        {
            Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
        }
        else {
            cursor.moveToFirst();
            List<String> list = new ArrayList<>();
            do {
                changeTime = cursor.getString(0);
                status = cursor.getString(2);
                String a = changeTime + " "+ status;
                list.add(a);
                display(list);
            }while (cursor.moveToNext());
        }

    }
    void display(List<String> listArray)
    {
        List<String> lI = listArray;
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, lI);listView.setAdapter(adapter);
    }
}