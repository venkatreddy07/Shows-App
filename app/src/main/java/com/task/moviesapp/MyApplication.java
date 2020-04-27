package com.task.moviesapp;



import android.app.Application;

import com.task.moviesapp.repositrories.AppSharedPreferences;

public class MyApplication extends Application {

    public static AppSharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        sharedPreferences = AppSharedPreferences.getInstance(this);
    }
}
