package com.task.moviesapp.repositrories;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSharedPreferences {

    private static SharedPreferences preferences;
    private static AppSharedPreferences instance;

    private static Context context;

    private AppSharedPreferences(Context context) {
        AppSharedPreferences.context = context;
        createInstance();
    }

    private static void createInstance() {
        if (preferences == null) {
            synchronized (AppSharedPreferences.class) {
                if (preferences == null) {
                    preferences = context.getSharedPreferences(context
                            .getPackageName(), Context.MODE_PRIVATE);
                }
            }
        }
    }

    public static AppSharedPreferences getInstance(Context context) {
        if (instance == null) {
            synchronized (AppSharedPreferences.class) {
                if (instance == null) {
                    instance = new AppSharedPreferences(context);
                }
            }
        }
        return instance;
    }

    public void setStringPreference(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

    public String getStringSharedPreference(String key, String elseValue) {
        return preferences.getString(key, elseValue);
    }
}
