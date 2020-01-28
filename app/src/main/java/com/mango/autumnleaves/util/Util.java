package com.mango.autumnleaves.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class Util {

    public static void saveData (String DBNAME, String Tablekey, String value, Context context){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(Tablekey, value);
        editor.commit();
    }

    public static String getData (String DBNAME, String Tablekey, Context context){

        SharedPreferences settings;
        String text;
        settings = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE);
        text = settings.getString(Tablekey, null);
        return text;
    }

    public static void deleteData (String DBNAME, Context context){

        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.clear();
        editor.commit();
    }

    //the constants
    public static void saveDataInt (String DBNAME, String Tablekey, int value, Context context){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putInt(Tablekey, value);
        editor.commit();
    }

    public static int getDataInt (String DBNAME, String Tablekey, Context context){

        SharedPreferences settings;
        int text;
        settings = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE);
        text = settings.getInt(Tablekey, 0);
        return text;
    }

    public static void deleteDataInt (String DBNAME, Context context){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.clear();
        editor.commit();
    }

    public static void toastShow(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

    }

    public static void toastShow(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }
}
