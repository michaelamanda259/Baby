package com.example.baby;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "myDB.db";
    private static final String TABLE_NAME = "Parent";
    public static final String NAME = "name";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String PASSWORD = "password";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "myDB.db", null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + TABLE_NAME + "  (" + NAME + " text , " + USERNAME + " text , " + EMAIL + " text , " + PHONE + " INTEGER  PRIMARY KEY, " + PASSWORD + " text)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);

    }
    public boolean add(ParentModel parentModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NAME,parentModel.getName());
        cv.put(USERNAME,parentModel.getUsername());
        cv.put(EMAIL,parentModel.getEmail());
        cv.put(PHONE,parentModel.getPhoneNumber());
        cv.put(PASSWORD,parentModel.getPassword());

        long insert = db.insert(TABLE_NAME, null, cv);
        if (insert == -1)
            return false;
        else
            return true;
    }

    public boolean find(String username,String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {username, password};

        Cursor cursor = db.rawQuery(" SELECT * FROM Parent WHERE username=? and password=?", new String[] { username,password});
        if (cursor.getCount()>0) return true;
        else return false;
    }
}
