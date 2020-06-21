package com.example.baby;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.core.content.ContextCompat;

public class SessionManagement {
    private static SharedPreferences.Editor editor;
    private static String SHARED_KEY= "session_user ";
    private static String SHARED_KEY2= "session_PHONE";

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
    public static void saveChildid(ChildModel childModel) {
        int child_id = childModel.getPhone();
        editor.putInt(SHARED_KEY2,child_id).commit();

    }

    public String getSession() {
        return sharedPreferences.getString(SHARED_KEY,"ERROR");

    }
    public int getSessionChild() {
        return sharedPreferences.getInt(SHARED_KEY2,1);

    }

    public void removeSession() {

        editor.putString(SHARED_KEY,null).commit();
    }
    public void removeSessionChild() {

        editor.putInt(SHARED_KEY2,0).commit();
    }
}
