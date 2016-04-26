package com.dodotdo.himsadmin.utill;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Omjoon on 16. 4. 21..
 */
public class MySharedfreferenceManager {
    static MySharedfreferenceManager instance;
    private static final String USER_ID = "user_id";
    private static final String USER_PW = "user_pw";
    SharedPreferences prefs;
    public static MySharedfreferenceManager getInstance(Context context){
        if(instance == null){
            instance = new MySharedfreferenceManager(context);
        }

        return instance;
    }

    MySharedfreferenceManager(Context context){
        prefs = context.getSharedPreferences("DEFAUT",context.MODE_PRIVATE);
    }

    public String getUserID(){
       return prefs.getString(USER_ID,null);
    }

    public String getPasswordID(){
        return prefs.getString(USER_PW,null);
    }

    public void setUserID(String userID){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USER_ID,userID);
        editor.commit();
    }

    public void setPasswordID(String passwordID){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USER_PW,passwordID);
        editor.commit();
    }

    public void clearAccount(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(USER_PW);
        editor.remove(USER_ID);
        editor.commit();
    }
}
