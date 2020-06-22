package com.example.baby;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "myDB.db";

    private static final String DIAPER = "diaper";
    private static final String FEEDING = "feding";
    private static final String MEDICATION = "medication";
    private static final String NOTE = "notes";
    private static final String PUMPING = "pumping";
    private static final String SLEEP = "sleep";
    //parent variable
    private static final String PARENT = "Parent";
    public static final String NAME = "name";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String PASSWORD = "password";

    //Child variables
    private static final String CHILD = "child";
    public static final String CHILD_ID = "phone";
    public static final String CHILD_NAME = "child_name";
    public static final String GENDER = "gender";
    public static final String DOB = "DOB";
    public static final String BLOODGROUP = "bloodgroup";

    public static final int VERSION = 1;
    private String userName;
    public int phone;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String parentTable = "CREATE TABLE IF NOT EXISTS " + PARENT + " (" + NAME + " TEXT , " + USERNAME + " TEXT, " + EMAIL + " TEXT, " + PHONE + " INTEGER  PRIMARY KEY, " + PASSWORD + " TEXT)";
        db.execSQL(parentTable);//make user name unique

        db.execSQL("CREATE TABLE IF NOT EXISTS child (phone	INTEGER PRIMARY KEY , child_name TEXT, gender TEXT, DOB DATE, bloodgroup TEXT, FOREIGN KEY(phone) REFERENCES " + PARENT + "(phone))");

        db.execSQL("CREATE TABLE IF NOT EXISTS diaper (diaper_id INTEGER PRIMARY KEY AUTOINCREMENT,change_time TIME,date DATE,status TEXT, phone INTEGER, FOREIGN KEY(phone) REFERENCES child(phone))");

        db.execSQL("CREATE TABLE IF NOT EXISTS feedingbottle (feedingb_id INTEGER PRIMARY KEY AUTOINCREMENT, phone INTEGER, date DATE,time TIME,type TEXT,quantity INTEGER, FOREIGN KEY(phone) REFERENCES child (phone))");
        db.execSQL("CREATE TABLE IF NOT EXISTS feedingnursing (feedingn_id INTEGER PRIMARY KEY AUTOINCREMENT, phone INTEGER, left_time INTEGER, right_time INTEGER,total_time INTEGER, date DATE, time TIME,FOREIGN KEY(phone) REFERENCES child (phone))");
        db.execSQL("CREATE TABLE IF NOT EXISTS feedingsolids (feedings_id INTEGER PRIMARY KEY AUTOINCREMENT, phone INTEGER, date DATE,quantity INTEGER,time TIME,food_content text, FOREIGN KEY(phone) REFERENCES child (phone))");

        db.execSQL("CREATE TABLE IF NOT EXISTS medication (medication_id INTEGER PRIMARY KEY AUTOINCREMENT, phone INTEGER,date DATE, time TIME, dosage INTEGER, med_name TEXT, FOREIGN KEY(phone) REFERENCES child(phone))");
        db.execSQL("CREATE TABLE IF NOT EXISTS sleep (sleep_id INTEGER PRIMARY KEY AUTOINCREMENT, date DATE,start_time TIME, end_time TIME,total TIME, phone INTEGER, FOREIGN KEY(phone) REFERENCES child (phone))");
        db.execSQL("CREATE TABLE IF NOT EXISTS notes (note_id INTEGER PRIMARY KEY AUTOINCREMENT,date DATE, time TIME, text TEXT, " + PHONE + " INTEGER, FOREIGN KEY(phone) REFERENCES " + PARENT + "(phone))");
        db.execSQL("CREATE TABLE IF NOT EXISTS pumping (pumping_id INTEGER PRIMARY KEY AUTOINCREMENT, total_quantity INTEGER, date DATE,time TIME, left_time TIME, right_time TIME, left_quantity INTEGER, right_quantity INTEGER, " + PHONE + " INTEGER, FOREIGN KEY(phone) REFERENCES " + PARENT + "(phone))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    public boolean login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(" SELECT * FROM Parent WHERE username=? and password=?", new String[]{username, password});
        if (cursor.getCount() > 0) return true;
        else return false;
    }

    public int parentPhone(String username)//to get session values from session and phone values from databse
    {

        SQLiteDatabase db = this.getReadableDatabase();
        this.userName = username;

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(" SELECT phone FROM Parent WHERE username=? ", new String[]{userName});
        cursor.moveToFirst();
        phone = cursor.getInt(cursor.getColumnIndex("phone"));
        return phone;
    }

    public Cursor userData (int phone)    {
        String phone1 = String.valueOf(phone);

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Parent WHERE phone = ?",new String[]{phone1});
        return cursor;
    }

    public Cursor childData(int child_id) {
        String child_id1 = String.valueOf(child_id);

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM child WHERE phone = ?",new String[]{child_id1});
        return cursor;

    }

    public boolean addParent(ParentModel parentModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NAME, parentModel.getName());
        cv.put(USERNAME, parentModel.getUsername());
        cv.put(EMAIL, parentModel.getEmail());
        cv.put(PHONE, parentModel.getPhoneNumber());
        cv.put(PASSWORD, parentModel.getPassword());

        long insert = db.insert(PARENT, null, cv);
        if (insert == -1) return false;
        else return true;
    }

    public boolean addChild(ChildModel childModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        String name = childModel.getName();
        String gender = childModel.getGender();
        String dob = childModel.getDOB();
        String bloodgroup = childModel.getBloodgroup();
        int phone = childModel.getPhone();

        ContentValues cv = new ContentValues();
        cv.put(CHILD_NAME, name);
        cv.put(GENDER, gender);
        cv.put(DOB, dob);
        cv.put(BLOODGROUP, bloodgroup);
        cv.put(PHONE, phone);

        long insert = db.insert(CHILD, null, cv);
        if (insert == -1) return false;
        else return true;
    }

    public boolean addDiaper(String date,String time, String status,  int child_id){
        SQLiteDatabase sql = this.getWritableDatabase();
         ContentValues cv =  new ContentValues();
         cv.put("change_time",time);
         cv.put("date",date);
         cv.put("status",status);
         cv.put("phone",child_id);

         long insert = sql.insert(DIAPER,null,cv);
         if (insert == -1) return false;
         else return true;
    }

    public boolean addNote(String date, String time, String note,int phone) {
        SQLiteDatabase sql = this.getWritableDatabase();
        ContentValues cv =  new ContentValues();
        cv.put("time",time);
        cv.put("date",date);
        cv.put("text",note);
        cv.put("phone",phone);

        long insert = sql.insert(NOTE,null,cv);
        if (insert == -1) return false;
        else return true;

    }

    public boolean addMedName(String date, String time, String medName, int quantity, int child_id) {
        SQLiteDatabase sql = this.getWritableDatabase();
        ContentValues cv =  new ContentValues();
        cv.put("time",time);
        cv.put("date",date);
        cv.put("med_name",medName);
        cv.put("dosage",quantity);
        cv.put("phone",child_id);

        long insert = sql.insert(MEDICATION,null,cv);
        if (insert == -1) return false;
        else return true;

    }



}