package com.libin.library.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.libin.library.MyApplication;

public class PreferenceUtil
{
    public static final String PRE_HCR_APP = "com.libin";
    public static void write(String key, String value) {
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(PRE_HCR_APP,
                Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).commit();
    }

    public static void write(String key, int value) {
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(PRE_HCR_APP,
                Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(key, value).commit();
    }

    public static void write(String key, long value) {
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(PRE_HCR_APP,
                Context.MODE_PRIVATE);
        sharedPreferences.edit().putLong(key, value).commit();
    }

    public static void write(String key, Boolean value) {
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(PRE_HCR_APP,
                Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    public static String readString(String key) {
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(PRE_HCR_APP,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static int readInt(String key) {
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(PRE_HCR_APP,
                Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }

    public static long readLong(String key) {
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(PRE_HCR_APP,
                Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, 0);
    }

    public static Boolean readBoolean(String key) {
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(PRE_HCR_APP,
                Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    public static void remove(String key) {
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(PRE_HCR_APP,
                Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(key).commit();
    }


}
