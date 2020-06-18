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

    private static final String DIAPER = "Diaper";
    private static final String FEEDING = "Feeding";
    private static final String MEDICATION = "Medication";
    private static final String NOTES = "Notes";
    private static final String PUMPING = "Pumping";
    private static final String SLEEP = "Sleep";
//parent variable
    private static final String PARENT = "Parent";
    public static final String NAME = "name";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String PASSWORD = "password";

//Child variables
    private static final String CHILD = "child";
    public static final String CHILD_ID = "child_id";
    public static final String CHILD_NAME = "child_name";
    public static final String GENDER = "gender";
    public static final String DOB = "DOB";
    public static final String BLOODGROUP = "bloodgroup";

//Diaper variables
    public static final String DIAPER_ID = "diaper_id";
    public static final String CHANGE_TIME = "change_time";
    public static final String DATE = "date";
    public static final String STATUS = "status";

//Medication variables
    public static final String MEDICATION_ID = "medication_id";
    public static final String DOSAGE = "dosage";
    public static final String MEDNAME = "medname";

//note variables
    public static final String NOTE_ID = "note_id";
    public static final String TEXT = "text";

//pumping variables
    public static final String PUMPING_ID = "pumping_id";
    public static final String TOTAL_QUANTITY = "total_quantity";
    public static final String START_TIME = "start_time";
    public static final String END_TIME = "end_time";
    public static final String LEFT_QUANTITY = "left_quantity";
    public static final String RIGHT_QUANTITY = "right_quantity";

//sleep variables
    public static final String SLEEP_ID = "sleep_id";
    public static final int VERSION = 1;


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null , VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String parentTable = "CREATE TABLE IF NOT EXISTS " + PARENT + " (" + NAME + " TEXT , " + USERNAME + " TEXT, " + EMAIL + " TEXT, " + PHONE + " INTEGER  PRIMARY KEY, " + PASSWORD + " TEXT)";
        db.execSQL(parentTable);
        db.execSQL("CREATE TABLE IF NOT EXISTS child (child_id	INTEGER PRIMARY KEY AUTOINCREMENT, child_name TEXT, gender TEXT, DOB NUMERIC, bloodgroup TEXT,phone INTEGER , FOREIGN KEY(phone) REFERENCES \" + PARENT +\"(phone))");
        db.execSQL("CREATE TABLE IF NOT EXISTS diaper (diaper_id INTEGER PRIMARY KEY AUTOINCREMENT,change_time NUMERIC,date NUMERIC,status TEXT, child_id INTEGER, FOREIGN KEY(child_id) REFERENCES child(child_id))");
        db.execSQL("CREATE TABLE IF NOT EXISTS feeding (feeding_id INTEGER PRIMARY KEY AUTOINCREMENT, child_id INTEGER, start_time NUMERIC, end_time NUMERIC, date NUMERIC, FOREIGN KEY(child_id) REFERENCES child (child_id))");
        db.execSQL("CREATE TABLE IF NOT EXISTS medication (medication_id INTEGER PRIMARY KEY AUTOINCREMENT, date NUMERIC, dosage INTEGER, med_name TEXT, FOREIGN KEY(child_id) REFERENCES child(child_id))");
        db.execSQL("CREATE TABLE IF NOT EXISTS notes (note_id INTEGER PRIMARY KEY AUTOINCREMENT, text TEXT NOT NULL, \"+ PHONE + \" INTEGER, FOREIGN KEY(phone) REFERENCES \" + PARENT +\"(phone))");
        db.execSQL("CREATE TABLE IF NOT EXISTS pumping (pumping_id INTEGER PRIMARY KEY AUTOINCREMENT, total_quantity INTEGER, date NUMERIC, start_time NUMERIC, end_time NUMERIC, left_quantity INTEGER, right_quantity INTEGER, "+ PHONE + " INTEGER, FOREIGN KEY(phone) REFERENCES " + PARENT +"(phone))");
        db.execSQL("CREATE TABLE IF NOT EXISTS sleep (sleep_id INTEGER PRIMARY KEY AUTOINCREMENT, date NUMERIC,start_time NUMERIC, end_time NUMERIC, child_id INTEGER, FOREIGN KEY(child_id) REFERENCES child (child_id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

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

    public boolean addChild(ChildModel childModel)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        //String isql131="insert into child(child_id, child_name,gender,DOB,bloodgroup,phone) values (103,'Vidya','F',123456,'AB',1234560987)";
      //  db.execSQL(isql131);
        //public ChildModel(String name, String gender, String blood_group, int child_id, int DOB, int phone) {
/*
        SQLiteDatabase db = this.getWritableDatabase();
*/
ChildModel childModel1 ;
String name = childModel.getName();
String gender = childModel.getGender();
int dob = childModel.getDOB();
String bloodgroup = childModel.getBloodgroup();
int phone = childModel.getPhone();

        ContentValues cv = new ContentValues();
        cv.put(CHILD_NAME,name);
        cv.put(GENDER,gender);
        cv.put(DOB,dob);
        cv.put(BLOODGROUP,bloodgroup);
        cv.put(PHONE,phone);

        long insert = db.insert(CHILD,null,cv);
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
