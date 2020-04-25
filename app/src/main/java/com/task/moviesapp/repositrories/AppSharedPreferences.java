package com.task.moviesapp.repositrories;

import android.content.Context;
import android.content.SharedPreferences;

import com.task.moviesapp.AppApplication;

public class AppSharedPreferences {

    private static SharedPreferences preferences;
    private static AppSharedPreferences instance;

    private AppSharedPreferences() {
        createInstance();
    }

    private static void createInstance() {
        if (preferences == null) {
            synchronized (AppSharedPreferences.class) {
                if (preferences == null) {
                    preferences = AppApplication.getContext().getSharedPreferences(AppApplication.getContext()
                            .getPackageName(), Context.MODE_PRIVATE);
                }
            }
        }
    }

    public static AppSharedPreferences getInstance() {
        if (instance == null) {
            synchronized (AppSharedPreferences.class) {
                if (instance == null) {
                    instance = new AppSharedPreferences();
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
