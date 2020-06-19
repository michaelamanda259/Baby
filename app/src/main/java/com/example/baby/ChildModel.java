package com.example.baby;

import android.database.sqlite.SQLiteDatabase;

/*
CREATE TABLE IF NOT EXISTS " + CHILD +"
(child_id	INTEGER PRIMARY KEY AUTOINCREMENT, child_name TEXT, gender TEXT, DOB NUMERIC, blood_group TEXT,"+ PHONE +" INTEGER ," +
"FOREIGN KEY("+ PHONE +") REFERENCES " + PARENT +"("+ PHONE +") ON DELETE CASCADE)";
        db.execSQL(childTable);

CREATE TABLE IF NOT EXISTS  " + DAIPER +
"(diaper_id INTEGER PRIMARY KEY AUTOINCREMENT,change_time NUMERIC,date NUMERIC,status TEXT, child_id INTEGER, " +
"FOREIGN KEY("+CHILD_ID+") REFERENCES " + CHILD + "("+CHILD_ID+") ON DELETE CASCADE)";
        db.execSQL(diaper);

CREATE TABLE IF NOT EXISTS " + FEEDING +
"(feeding_id INTEGER PRIMARY KEY AUTOINCREMENT, child_id INTEGER, start_time NUMERIC, end_time NUMERIC, date NUMERIC, " +
"FOREIGN KEY("+CHILD_ID+") REFERENCES " + CHILD + "("+CHILD_ID+") ON DELETE CASCADE)";
        db.execSQL(feeding);

CREATE TABLE IF NOT EXISTS " + MEDICATION +
"(medication_id INTEGER PRIMARY KEY AUTOINCREMENT, date NUMERIC,dosage INTEGER,name TEXT, "+
"FOREIGN KEY("+CHILD_ID+") REFERENCES " + CHILD + "("+CHILD_ID+") ON DELETE CASCADE)";
        db.execSQL(medication);

CREATE TABLE IF NOT EXISTS " + NOTES +
"(note_id INTEGER PRIMARY KEY AUTOINCREMENT, text TEXT NOT NULL, phone INTEGER," +
"FOREIGN KEY("+ PHONE +") REFERENCES " + PARENT +"("+ PHONE +") ON DELETE CASCADE)";
        db.execSQL(notes);

CREATE TABLE IF NOT EXISTS  " + PUMPING +
"(pumping_id INTEGER PRIMARY KEY AUTOINCREMENT, total_quantity INTEGER, date NUMERIC, " +
"start_time NUMERIC, end_time NUMERIC, left_quantity INTEGER, right_quantity INTEGER, phone INTEGER," +
"FOREIGN KEY("+ PHONE +") REFERENCES " + PARENT +"("+ PHONE +") ON DELETE CASCADE)";
        db.execSQL(pumping);

CREATE TABLE IF NOT EXISTS " + SLEEP +
"(sleep_id INTEGER PRIMARY KEY AUTOINCREMENT, date NUMERIC,start_time NUMERIC,end_time NUMERIC, child_id INTEGER," +
"FOREIGN KEY("+ CHILD_ID +") REFERENCES " + CHILD + "("+CHILD_ID+") ON DELETE CASCADE)";
        db.execSQL(sleep);
 */
public class ChildModel {
    /*
    (child_id	INTEGER PRIMARY KEY AUTOINCREMENT,
    child_name TEXT, gender TEXT,
    DOB NUMERIC,
    blood_group TEXT,
    "+ PHONE +" INTEGER ," + "FOREIGN KEY("+ PHONE +") REFERENCES " + PARENT +"("+ PHONE +") ON DELETE CASCADE)";
*/
    private String name,DOB,gender,bloodgroup;
    private int child_id, phone;
//103,'Vidya','F',123456,'AB',1234560987
    public ChildModel( String name, String gender,  String DOB, String bloodgroup,int phone) {
        this.name = name;
        this.gender = gender;
        this.bloodgroup = bloodgroup;
        this.DOB = DOB;
        this.phone = phone;
    }

    public ChildModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBlood_group(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public int getChild_id() {
        return child_id;
    }

    public void setChild_id(int child_id) {
        this.child_id = child_id;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }
    /*  SetBabyDetail setBabyDetail = new SetBabyDetail();
            SQLiteDatabase db = this.getReadableDatabase();
            String username = setBabyDetail.parentLogin();
            Log.i(TAG,username);
            int phone=0;
             Cursor resultSet = mydatbase.rawQuery("Select * from TutorialsPoint",null);
    resultSet.moveToFirst();
    String username = resultSet.getString(0);
    String password = resultSet.getString(1);
     @SuppressLint("Recycle") Cursor cursor = db.rawQuery(" SELECT phone FROM Parent WHERE username=? ", new String[] { username});
            cursor.moveToFirst();
            phone = cursor.getInt(0);
            return phone;
        }*/
    public int getPhone() {
//        int phone = DatabaseHelper.parentPhone();
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "ChildModel{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", bloodgroup='" + bloodgroup + '\'' +
                ", child_id=" + child_id +
                ", DOB=" + DOB +
                ", phone=" + phone +
                '}';
    }
}
