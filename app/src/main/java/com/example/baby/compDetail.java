package com.example.baby;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class compDetail extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    Cursor cursor;
    ArrayList<String> list = new ArrayList<>();
    ListView listView;
    TextView textView;
    Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp_detail);
        intent = getIntent();

        databaseHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.detail);
        textView =findViewById(R.id.userName);
        String t = intent.getStringExtra("text");
        compDetail(t);
        textView.setText(intent.getStringExtra("name"));


    }

    void compDetail(String text)
    {
        String time, text1, text2, text3, text4, text5;
        cursor = databaseHelper.detail(text);
        if (cursor.getCount() == 0) {
            Toast.makeText(compDetail.this,"No children  ",Toast.LENGTH_LONG).show();

        } else if (cursor.getCount() > 0) {
            Toast.makeText(this, " Child details available " , Toast.LENGTH_SHORT).show();

            cursor.moveToFirst();
//            Toast.makeText(this, "Column : " +cursor.getColumnCount(), Toast.LENGTH_SHORT).show();

            do {

                text1 = cursor.getString(0);
                text2 = cursor.getString(1);
                text3 = cursor.getString(2);
                text4 = cursor.getString(3);
                text5 = cursor.getString(4);

                list.add("Name :" + text2 + "       Gender :"+ text3+ "\nDOB :"+ text4+"         Blood Group "+text5);
                display(list);
            } while (cursor.moveToNext());
        }
        else {
            Toast.makeText(this, " No details available " , Toast.LENGTH_SHORT).show();

        }
    }


    void display(List<String> listArray) {
        List<String> lI = listArray;

        if (lI.isEmpty()) {
            Toast.makeText(this, " No details available " , Toast.LENGTH_SHORT).show();
        } else {
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, lI);
            listView.setAdapter(adapter);
                   }

    }

    public void deleteUser(View view)
    {
        DatabaseHelper databaseHelper  = new DatabaseHelper(this);
        boolean success = databaseHelper.deleteuser(intent.getStringExtra("text"));
        Toast.makeText(this,"Deleted user Successfully..? "+ success, Toast.LENGTH_SHORT).show();
        if (success )
        {
            Intent intent = new Intent(this, admin.class);
            startActivity(intent);

        }
    }
}