package com.hinext.maxis7567.karjoo.services;

import android.content.Context;
import android.content.SharedPreferences;



public class DataBaseTokenID {
    private static final String TOKEN_DB = "T";
    private static final String TOKEN = "Token";

    public static final String DEFAULT_TOKEN_ID ="DefaultTokenID";

    public static void WriteTokenID(Context context, String token) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(TOKEN_DB, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(TOKEN, token);
        editor.commit();
    }
    public static void ResetTokenID(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(TOKEN_DB, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(TOKEN,DataBaseTokenID.DEFAULT_TOKEN_ID);
        editor.commit();
    }
    public static String GetTokenID(Context context) {
        SharedPreferences settings;
        settings = context.getSharedPreferences(TOKEN_DB, Context.MODE_PRIVATE);
        return  settings.getString(TOKEN, DataBaseTokenID.DEFAULT_TOKEN_ID);
    }
    public static boolean isLogin(Context context) {
        SharedPreferences settings;
        settings = context.getSharedPreferences(TOKEN_DB, Context.MODE_PRIVATE);
        return  (!settings.getString(TOKEN, DataBaseTokenID.DEFAULT_TOKEN_ID).equals(DEFAULT_TOKEN_ID));
    }
}