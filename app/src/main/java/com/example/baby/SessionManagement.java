package com.example.baby;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.core.content.ContextCompat;

public class SessionManagement {
    private static SharedPreferences.Editor editor;
    private static String SHARED_KEY= "session_user ";
    SharedPreferences sharedPreferences;
    String SHARED_PREF_NAME = "session";

    public SessionManagement(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static void saveSession(ParentModel parentModel) {
        String username = parentModel.getUsername();
        editor.putString(SHARED_KEY,username).commit();

    }

    public String getSession() {
        return sharedPreferences.getString(SHARED_KEY,"ERROR");

    }

    public void removeSession() {

        editor.putString(SHARED_KEY,null).commit();
    }
}
