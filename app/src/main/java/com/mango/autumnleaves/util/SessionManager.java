package com.mango.autumnleaves.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.mango.autumnleaves.Activity.DashboardActivity;
import com.mango.autumnleaves.Activity.LoginActivity;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String userName,String userPass){
        editor.putBoolean(LOGIN,true);
        editor.putString(USERNAME,userName);
        editor.putString(PASSWORD,userPass);
        editor.apply();
    }

    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN,false);
    }

    public void checkLogin(){
        if(!this.isLoggin()){
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
            ((DashboardActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String,String> user = new HashMap<>();
        user.put(USERNAME,sharedPreferences.getString(USERNAME,null));
        user.put(PASSWORD,sharedPreferences.getString(PASSWORD,null));
        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context,LoginActivity.class);
        context.startActivity(i);
        ((DashboardActivity) context).finish();
    }
}
