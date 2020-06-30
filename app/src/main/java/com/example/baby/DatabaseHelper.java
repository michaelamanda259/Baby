package com.example.baby;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "myDB.db";

    private static final String DIAPER = "diaper";
    private static final String FEEDINGBOTTLE = "feedingbottle";
    private static final String FEEDINGNURSING = "feedingnursing";
    private static final String FEEDINGSOLIDS = "feedingsolids";

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
    Cursor cursor;

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
        db.execSQL("CREATE TABLE IF NOT EXISTS feedingnursing (feedingn_id INTEGER PRIMARY KEY AUTOINCREMENT, phone INTEGER, left_time TIME, right_time TIME,total_time TIME, date DATE, time TIME,FOREIGN KEY(phone) REFERENCES child (phone))");
        db.execSQL("CREATE TABLE IF NOT EXISTS feedingsolids (feedings_id INTEGER PRIMARY KEY AUTOINCREMENT, phone INTEGER, date DATE,quantity INTEGER,time TIME,food_content text, FOREIGN KEY(phone) REFERENCES child (phone))");

        db.execSQL("CREATE TABLE IF NOT EXISTS medication (medication_id INTEGER PRIMARY KEY AUTOINCREMENT, phone INTEGER,date DATE, time TIME, dosage INTEGER, med_name TEXT, FOREIGN KEY(phone) REFERENCES child(phone))");
        db.execSQL("CREATE TABLE IF NOT EXISTS sleep (sleep_id INTEGER PRIMARY KEY AUTOINCREMENT, date DATE,start_time TIME, end_time TIME,total TIME, phone INTEGER, FOREIGN KEY(phone) REFERENCES child (phone))");
        db.execSQL("CREATE TABLE IF NOT EXISTS notes (note_id INTEGER PRIMARY KEY AUTOINCREMENT,date DATE, time TIME, text TEXT, " + PHONE + " INTEGER, FOREIGN KEY(phone) REFERENCES " + PARENT + "(phone))");
        db.execSQL("CREATE TABLE IF NOT EXISTS pumping (pumping_id INTEGER PRIMARY KEY AUTOINCREMENT,  date DATE,time TIME, left_time TIME, right_time TIME,total_time TIME,total_quantity INTEGER, left_quantity INTEGER, right_quantity INTEGER, " + PHONE + " INTEGER, FOREIGN KEY(phone) REFERENCES " + PARENT + "(phone))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    public boolean login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        cursor = db.rawQuery(" SELECT * FROM Parent WHERE username=? and password=?", new String[]{username, password});
        if (cursor.getCount() > 0) return true;
        else return false;
    }

    public int parentPhone(String username)//to get session values from session and phone values from databse
    {

        SQLiteDatabase db = this.getReadableDatabase();
        this.userName = username;

        cursor = db.rawQuery(" SELECT phone FROM Parent WHERE username=? ", new String[]{userName});
        cursor.moveToFirst();
        phone = cursor.getInt(cursor.getColumnIndex("phone"));
        return phone;
    }

    public Cursor userData (int phone)    {
        String phone1 = String.valueOf(phone);

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor1 = sqLiteDatabase.rawQuery("SELECT * FROM Parent WHERE phone = ?",new String[]{phone1});
        return cursor1;
    }

    public Cursor childData(int child_id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM child WHERE phone = ?",new String[]{String.valueOf(child_id)});
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

    public boolean addBottleFeeding(String date, String time, String type, int quantity, int child_id) {
        SQLiteDatabase sql = this.getWritableDatabase();
        ContentValues cv =  new ContentValues();
        cv.put("time",time);
        cv.put("date",date);
        cv.put("type",type);
        cv.put("quantity",quantity);
        cv.put("phone",child_id);

        long insert = sql.insert(FEEDINGBOTTLE,null,cv);
        if (insert == -1) return false;
        else return true;
    }

    public boolean addSolidFeeding(String date, String time, String food_content, int quantity, int child_id) {
        SQLiteDatabase sql = this.getWritableDatabase();
        ContentValues cv =  new ContentValues();
        cv.put("time",time);
        cv.put("date",date);
        cv.put("food_content",food_content);
        cv.put("quantity",quantity);
        cv.put("phone",child_id);

        long insert = sql.insert(FEEDINGSOLIDS,null,cv);
        if (insert == -1) return false;
        else return true;
    }

    public boolean addSleep(String date, String start_time, String end_time, String total, int child_id) {
        SQLiteDatabase sql = this.getWritableDatabase();
        ContentValues cv =  new ContentValues();
        cv.put("date",date);
        cv.put("start_time",start_time);
        cv.put("end_time",end_time);
        cv.put("total",total);
        cv.put("phone",child_id);

        long insert = sql.insert(SLEEP,null,cv);
        if (insert == -1) return false;
        else return true;
    }

    public boolean addBreastFeeding(String date, String time, String lefttime, String righttime, String totaltime, int child_id) {
        SQLiteDatabase sql = this.getWritableDatabase();
        ContentValues cv =  new ContentValues();
        cv.put("time",time);
        cv.put("date",date);
        cv.put("left_time",lefttime);
        cv.put("right_time",righttime);
        cv.put("total_time",totaltime);
        cv.put("phone",child_id);

        long insert = sql.insert(FEEDINGNURSING,null,cv);
        if (insert == -1) return false;
        else return true;

    }

    public boolean addPumping(String date, String time, String lefttime, String righttime, String totaltime, int leftQ, int rightQ, int totalQ, int phone) {
        SQLiteDatabase sql = this.getWritableDatabase();
        ContentValues cv =  new ContentValues();
        cv.put("time",time);
        cv.put("date",date);
        cv.put("left_time",lefttime);
        cv.put("right_time",righttime);
        cv.put("total_time",totaltime);
        cv.put("right_time",rightQ);
        cv.put("left_quantity",leftQ);
        cv.put("total_quantity",totalQ);

        cv.put("phone",phone);

        long insert = sql.insert(PUMPING,null,cv);
        if (insert == -1) return false;
        else return true;
    }

    public Cursor recentActivitySleep(String date, int child_id) {
        Cursor cursor;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        cursor =
                sqLiteDatabase.rawQuery("SELECT start_time , end_time, total FROM sleep INNER JOIN child ON sleep.phone = sleep.phone WHERE sleep.date = ? and child.phone = ? ORDER by sleep.sleep_id",new String[]{date, String.valueOf(child_id)});
        return cursor;

    }

    public Cursor recentActivityDiaper(String date, int child_id) {
        Cursor cursor;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        cursor =
                sqLiteDatabase.rawQuery("SELECT change_time , date, status FROM diaper INNER JOIN child ON diaper.phone = child.phone WHERE diaper.date = ? and child.phone = ? ORDER by diaper.diaper_id",new String[]{date, String.valueOf(child_id)});
        return cursor;
    }

    public Cursor recentActivityFeedingN(String date, int child_id) {
        Cursor cursor;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        cursor =
                sqLiteDatabase.rawQuery("SELECT time, left_time, right_time ,  total_time FROM feedingnursing INNER JOIN child ON feedingnursing.phone = child.phone WHERE feedingnursing.date = ? and child.phone = ? ORDER by feedingnursing.feedingn_id",new String[]{date, String.valueOf(child_id)});
        return cursor;
    }

    public Cursor recentActivityFeedingB(String date, int child_id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        cursor =
                sqLiteDatabase.rawQuery("SELECT time, type, quantity FROM feedingbottle INNER JOIN child ON feedingbottle.phone = child.phone WHERE feedingbottle.date = ? and child.phone = ? ORDER by feedingbottle.feedingb_id",new String[]{date, String.valueOf(child_id)});
        return cursor;
    }

    public Cursor recentActivityFeedingS(String date, int child_id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        cursor =
                sqLiteDatabase.rawQuery("SELECT time, quantity, food_content FROM feedingsolids INNER JOIN child ON feedingsolids.phone = child.phone WHERE feedingsolids.date = ? and child.phone = ? ORDER by feedingsolids.feedings_id",new String[]{date, String.valueOf(child_id)});
        return cursor;
    }

    public Cursor recentActivityMedication(String date, int child_id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        cursor =
                sqLiteDatabase.rawQuery("SELECT time, dosage, med_name FROM medication INNER JOIN child ON medication.phone = child.phone WHERE medication.date = ? and child.phone = ? ORDER by medication.medication_id",new String[]{date, String.valueOf(child_id)});
        return cursor;
    }

    public Cursor recentActivityPumping(String date, int child_id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        cursor =
                sqLiteDatabase.rawQuery("SELECT time, total_quantity, total_time FROM pumping INNER JOIN parent ON pumping.phone = parent.phone WHERE pumping.date = ? and parent.phone = ? ORDER by pumping.pumping_id",new String[]{date, String.valueOf(child_id)});
        return cursor;
    }

    public Cursor recentActivityNotes(String date, int child_id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        cursor =
                sqLiteDatabase.rawQuery("SELECT time, text FROM notes INNER JOIN parent ON notes.phone = parent.phone WHERE notes.date = ? and parent.phone = ? ORDER by notes.note_id",new String[]{date, String.valueOf(child_id)});
        return cursor;
    }

    //all activity show functions

    public Cursor allActivityFeedingN(int child_id) {
        Cursor cursor;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        cursor=sqLiteDatabase.rawQuery("SELECT time, left_time, right_time , total_time, date FROM feedingnursing INNER JOIN child ON feedingnursing.phone = child.phone WHERE child.phone = ? ORDER by feedingnursing.feedingn_id",new String[]{String.valueOf(child_id)});
        return cursor;
    }

    public Cursor allActivityFeedingB(int child_id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        cursor =
                sqLiteDatabase.rawQuery("SELECT time, type, quantity, date FROM feedingbottle INNER JOIN child ON feedingbottle.phone = child.phone WHERE child.phone = ? ORDER by feedingbottle.feedingb_id",new String[]{String.valueOf(child_id)});
        return cursor;
    }

    public Cursor allActivityFeedingS(int child_id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        cursor =
                sqLiteDatabase.rawQuery("SELECT time, quantity, food_content, date FROM feedingsolids INNER JOIN child ON feedingsolids.phone = child.phone WHERE child.phone = ? ORDER by feedingsolids.feedings_id",new String[]{String.valueOf(child_id)});
        return cursor;
    }

    public Cursor allActivityPumping(int child_id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        cursor =
                sqLiteDatabase.rawQuery("SELECT time, total_quantity, total_time, date FROM pumping INNER JOIN parent ON pumping.phone = parent.phone WHERE parent.phone = ? ORDER by pumping.pumping_id",new String[]{String.valueOf(child_id)});
        return cursor;
    }

    public Cursor allActivitySleep(int child_id) {
        Cursor cursor;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        cursor =
                sqLiteDatabase.rawQuery("SELECT start_time , end_time, total, date FROM sleep INNER JOIN child ON sleep.phone = sleep.phone WHERE child.phone = ? ORDER by sleep.sleep_id",new String[]{String.valueOf(child_id)});
        return cursor;

    }

    public Cursor allActivityDiaper(int child_id) {
        Cursor cursor;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        cursor =
                sqLiteDatabase.rawQuery("SELECT change_time , date, status FROM diaper INNER JOIN child ON diaper.phone = child.phone WHERE child.phone = ? ORDER by diaper.diaper_id",new String[]{String.valueOf(child_id)});
        return cursor;
    }

    public Cursor allActivityMedication(int child_id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        cursor =
                sqLiteDatabase.rawQuery("SELECT time, dosage, med_name, date FROM medication INNER JOIN child ON medication.phone = child.phone WHERE medication.date = ? and child.phone = ? ORDER by medication.medication_id",new String[]{String.valueOf(child_id)});
        return cursor;
    }

    public Cursor allActivityNotes(int child_id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        cursor =
                sqLiteDatabase.rawQuery("SELECT time, text, date FROM notes INNER JOIN parent ON notes.phone = parent.phone WHERE parent.phone = ? ORDER by notes.note_id",new String[]{String.valueOf(child_id)});
        return cursor;
    }


}