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
    private static final String CHILD = "Child";
    public static final String CHILD_ID = "child_id";
    public static final String CHILD_NAME = "child_name";
    public static final String GENDER = "gender";
    public static final String DOB = "DOB";
    public static final String BLOOD_GROUP = "blood_group";

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


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String parentTable = "CREATE TABLE IF NOT EXISTS " + PARENT + "  " +
                "(" + NAME + " " + TEXT + " , " + USERNAME + " " + TEXT + " , " + EMAIL + " " + TEXT + " , " + PHONE + " INTEGER  PRIMARY KEY, " + PASSWORD + " " + TEXT + ")";

        String childTable = "CREATE TABLE IF NOT EXISTS " + CHILD +
                "(child_id	INTEGER PRIMARY KEY AUTOINCREMENT, " + CHILD_NAME + " TEXT, " + GENDER + " TEXT, " + DOB + " NUMERIC, " + BLOOD_GROUP + " TEXT," + PHONE +" INTEGER ," +
                "FOREIGN KEY("+ PHONE +") REFERENCES " + PARENT +"("+ PHONE +") ON DELETE CASCADE)";

      /*  String diaper = " CREATE TABLE IF NOT EXISTS  " + DIAPER +
                "(" + DIAPER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + CHANGE_TIME + " NUMERIC," + DATE + " NUMERIC," + STATUS + " TEXT, "+ CHILD_ID +" INTEGER, " +
                "FOREIGN KEY("+CHILD_ID+") REFERENCES " + CHILD + "("+CHILD_ID+") ON DELETE CASCADE)";


        String feeding = "CREATE TABLE IF NOT EXISTS " + FEEDING +
                "(feeding_id INTEGER PRIMARY KEY AUTOINCREMENT, child_id INTEGER, " + START_TIME + " NUMERIC, " + END_TIME + " NUMERIC, " + DATE + " NUMERIC, " +
                "FOREIGN KEY("+CHILD_ID+") REFERENCES " + CHILD + "("+CHILD_ID+") ON DELETE CASCADE)";


        String medication = "CREATE TABLE IF NOT EXISTS " + MEDICATION +
                "(" + MEDICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DATE + " NUMERIC, " + DOSAGE + " INTEGER, " + MEDNAME + " TEXT, " +
                "FOREIGN KEY("+CHILD_ID+") REFERENCES " + CHILD + "("+CHILD_ID+") ON DELETE CASCADE)";


        String notes = "CREATE TABLE IF NOT EXISTS " + NOTES +
                "(" + NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TEXT + " TEXT NOT NULL, "+ PHONE + " INTEGER," +
                "FOREIGN KEY("+ PHONE +") REFERENCES " + PARENT +"("+ PHONE +") ON DELETE CASCADE)";


        String pumping = " CREATE TABLE IF NOT EXISTS  " + PUMPING +
                "(" + PUMPING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TOTAL_QUANTITY + " INTEGER, " + DATE + " NUMERIC, " +
                START_TIME + " NUMERIC, " + END_TIME + " NUMERIC, " + LEFT_QUANTITY + " INTEGER, " + RIGHT_QUANTITY + " INTEGER, "+ PHONE + " INTEGER," +
                "FOREIGN KEY("+ PHONE +") REFERENCES " + PARENT +"("+ PHONE +") ON DELETE CASCADE)";

        String sleep = " CREATE TABLE IF NOT EXISTS " + SLEEP +
                "(" + SLEEP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DATE + " NUMERIC," + START_TIME + " NUMERIC," + END_TIME + " NUMERIC, "+ CHILD_ID +" INTEGER," +
                "FOREIGN KEY("+ CHILD_ID +") REFERENCES " + CHILD + "("+CHILD_ID+") ON DELETE CASCADE)";
*/
        db.execSQL(parentTable);
        db.execSQL(childTable);
       /* db.execSQL(diaper);
        db.execSQL(feeding);
        db.execSQL(medication);
        db.execSQL(notes);
        db.execSQL(pumping);
        db.execSQL(sleep);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String parentTable = "DROP TABLE IF EXISTS parentTable";
        String childTable = "DROP TABLE IF EXISTS childTable";
      /*  String diaper = "DROP TABLE IF EXISTS diaper";
        String feeding = "DROP TABLE IF EXISTS feeding";
        String medication = "DROP TABLE IF EXISTS medication";
        String notes = "DROP TABLE IF EXISTS notes";
        String pumping = "DROP TABLE IF EXISTS pumping";
        String sleep = "DROP TABLE IF EXISTS sleep";

        db.execSQL(parentTable);
        db.execSQL(childTable);
        db.execSQL(diaper);
        db.execSQL(feeding);
        db.execSQL(medication);
        db.execSQL(notes);
        db.execSQL(pumping);
        db.execSQL(sleep);
*/
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

    public boolean addChild(ChildModel childModel)
    {
        //public ChildModel(String name, String gender, String blood_group, int child_id, int DOB, int phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CHILD_NAME,childModel.getName());
        cv.put(DOB,childModel.getDOB());
        cv.put(GENDER,childModel.getGender());
        cv.put(BLOOD_GROUP,childModel.getBlood_group());

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
