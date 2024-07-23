package com.pro.resume.craft.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {

    private static final String PREFS_NAME = "MyPrefsFile";
    private static SharedPreferences sharedPreferences;

    // Save String value to SharedPreferences
    public static void saveString(Context context, String key, String value) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    // Get String value from SharedPreferences
    public static String getString(Context context, String key, String defaultValue) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

    // Save int value to SharedPreferences
    public static void saveInt(Context context, String key, int value) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    // Get int value from SharedPreferences
    public static int getInt(Context context, String key, int defaultValue) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
    }

    // Save boolean value to SharedPreferences
    public static void saveBoolean(Context context, String key, boolean value) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    // Get boolean value from SharedPreferences
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    // Save long value to SharedPreferences
    public static void saveLong(Context context, String key, long value) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    // Get long value from SharedPreferences
    public static long getLong(Context context, String key, long defaultValue) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, defaultValue);
    }

    // Save float value to SharedPreferences
    public static void saveFloat(Context context, String key, float value) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    // Get float value from SharedPreferences
    public static float getFloat(Context context, String key, float defaultValue) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(key, defaultValue);
    }

    // Clear a specific preference value from SharedPreferences
    public static void clearPreference(Context context, String key) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    // Clear all preferences from SharedPreferences
    public static void clearAllPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
