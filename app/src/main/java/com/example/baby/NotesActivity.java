package com.example.baby;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NotesActivity extends AppCompatActivity {

    ImageButton buttonBack,buttonSubmit;
    TimePickerDialog timePickerDialog;
    EditText editTextTime,editTextDate, editNote;
    DatabaseHelper databaseHelper;
    SessionManagement sm;


    public String getCurrentDate(){
        Calendar c = Calendar.getInstance();
        System.out.println(c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());

        return formattedDate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        editTextDate = findViewById(R.id.et_date);
        editTextTime = findViewById(R.id.et_time);
        editNote = findViewById(R.id.et_notes);
        buttonBack = findViewById(R.id.cancel_button);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotesActivity.this,Dashboard.class);
                startActivity(intent);
            }
        });

        editTextDate.setText(getCurrentDate());
        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, dayOfMonth);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
            private void updateLabel() {
                String myFormat = "dd-MMM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                editTextDate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(NotesActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void chooseTime(View view) {

        Calendar calendar = Calendar.getInstance();
        int hourOfDay=calendar.get(Calendar.HOUR);
        int minute=calendar.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(NotesActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                String am_pm;
                if (hourOfDay<12) {
                    am_pm = "AM";
                    editTextTime.setText(hourOfDay + ":" + minute + " " + am_pm);
                }
                else if (hourOfDay == 12){
                    am_pm = "PM";
                    editTextTime.setText(hourOfDay + ":" + minute + " " + am_pm);

                }
                else {
                    am_pm = "PM";
                    editTextTime.setText(hourOfDay + ":" + minute + " " + am_pm);

                }
            }
        },hourOfDay,minute,false);
        timePickerDialog.show();
    }

    private Boolean validateDate() {
        String val = editTextDate.getText().toString();

        if (val.isEmpty()) {
            editTextDate.setError("Field can not be empty");
            return false;
        } else {
            editTextDate.setError(null);
//            editTextDate.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateTime() {
        String val = editTextTime.getText().toString();

        if (val.isEmpty()) {
            editTextTime.setError("Field can not be empty");
            return false;
        } else {
            editTextTime.setError(null);
//            editTextDate.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean writeNote()
    {
        String val = editNote.getText().toString();

        if (val.isEmpty()) {
            editNote.setError("Field can not be empty");
            return false;
        } else {
            editNote.setError(null);
            return true;
        }
    }

    public void saveData(View view)
    {
        databaseHelper = new DatabaseHelper(NotesActivity.this);
        String note ,date , time;
        int childid;
        boolean success = false;
        if(!validateDate() | !validateTime() | !writeNote() ){
            Toast.makeText(NotesActivity.this,"Write something...", Toast.LENGTH_SHORT).show();

            return;
        }
        note = editNote.getText().toString();
        date = editTextDate.getText().toString();
        time = editTextTime.getText().toString();
        sm = new SessionManagement(NotesActivity.this);
        String username = sm.getSession();

        int phone = databaseHelper.parentPhone(username);

        try {
            success=databaseHelper.addNote(date,time,note,phone);
        } catch (Exception e) {
            databaseHelper.addNote("Error","Error","Error",0);
        }
        if (success) {
            Toast.makeText(NotesActivity.this, "Note Saved...?" + success, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(NotesActivity.this, Dashboard.class);
            startActivity(intent);
        }


    }


}
