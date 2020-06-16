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

private static final String DAIPER = "Daiper";
    private static final String FEEDING = "Feeding";
    private static final String MEDICATION = "Medication";
    private static final String NOTES = "Notes";
    private static final String PUMPING = "Pumping";
    private static final String SLEEP = "Sleep";

    private static final String PARENT = "Parent";
    public static final String NAME = "name";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String PASSWORD = "password";

    private static final String CHILD = "Child";
    public static final String CHILD_ID = "child_id";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String parentTable = "CREATE TABLE IF NOT EXISTS " + PARENT + "  " +
                "(" + NAME + " text , " + USERNAME + " text , " + EMAIL + " text , " + PHONE + " INTEGER  PRIMARY KEY, " + PASSWORD + " text)";

        db.execSQL(parentTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String childTable = "CREATE TABLE IF NOT EXISTS " + CHILD +
                "(child_id	INTEGER PRIMARY KEY AUTOINCREMENT, child_name TEXT, gender TEXT, DOB NUMERIC, blood_group TEXT,"+ PHONE +" INTEGER ," +
                "FOREIGN KEY("+ PHONE +") REFERENCES " + PARENT +"("+ PHONE +") ON DELETE CASCADE)";
        db.execSQL(childTable);

        String diaper = " CREATE TABLE IF NOT EXISTS  " + DAIPER +
                "(diaper_id INTEGER PRIMARY KEY AUTOINCREMENT,change_time NUMERIC,date NUMERIC,status TEXT, child_id INTEGER, " +
                "FOREIGN KEY("+CHILD_ID+") REFERENCES " + CHILD + "("+CHILD_ID+") ON DELETE CASCADE)";
        db.execSQL(diaper);

        String feeding = "CREATE TABLE IF NOT EXISTS " + FEEDING +
                "(feeding_id INTEGER PRIMARY KEY AUTOINCREMENT, child_id INTEGER, start_time NUMERIC, end_time NUMERIC, date NUMERIC, " +
                "FOREIGN KEY("+CHILD_ID+") REFERENCES " + CHILD + "("+CHILD_ID+") ON DELETE CASCADE)";
        db.execSQL(feeding);

        String medication = "CREATE TABLE IF NOT EXISTS " + MEDICATION +
                "(medication_id INTEGER PRIMARY KEY AUTOINCREMENT, date NUMERIC,dosage INTEGER,name TEXT, "+
                "FOREIGN KEY("+CHILD_ID+") REFERENCES " + CHILD + "("+CHILD_ID+") ON DELETE CASCADE)";
        db.execSQL(medication);

        String notes = "CREATE TABLE IF NOT EXISTS " + NOTES +
                "(note_id INTEGER PRIMARY KEY AUTOINCREMENT, text TEXT NOT NULL, phone INTEGER," +
                "FOREIGN KEY("+ PHONE +") REFERENCES " + PARENT +"("+ PHONE +") ON DELETE CASCADE)";
        db.execSQL(notes);

        String pumping = " CREATE TABLE IF NOT EXISTS  " + PUMPING +
                "(pumping_id INTEGER PRIMARY KEY AUTOINCREMENT, total_quantity INTEGER, date NUMERIC, " +
                "start_time NUMERIC, end_time NUMERIC, left_quantity INTEGER, right_quantity INTEGER, phone INTEGER," +
                "FOREIGN KEY("+ PHONE +") REFERENCES " + PARENT +"("+ PHONE +") ON DELETE CASCADE)";
        db.execSQL(pumping);

        String sleep = " CREATE TABLE IF NOT EXISTS " + SLEEP +
                "(sleep_id INTEGER PRIMARY KEY AUTOINCREMENT, date NUMERIC,start_time NUMERIC,end_time NUMERIC, child_id INTEGER," +
                "FOREIGN KEY("+ CHILD_ID +") REFERENCES " + CHILD + "("+CHILD_ID+") ON DELETE CASCADE)";
        db.execSQL(sleep);

    }

    public boolean addParent(ParentModel parentModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NAME,parentModel.getName());
        cv.put(USERNAME,parentModel.getUsername());
        cv.put(EMAIL,parentModel.getEmail());
        cv.put(PHONE,parentModel.getPhoneNumber());
        cv.put(PASSWORD,parentModel.getPassword());

        long insert = db.insert(PARENT, null, cv);
        if (insert == -1) return false;
        else return true;
    }

    public boolean login(String username,String password)    {
        SQLiteDatabase db = this.getReadableDatabase();

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(" SELECT * FROM Parent WHERE username=? and password=?", new String[] { username,password});
        if (cursor.getCount()>0) return true;
        else return false;
    }
}
