package com.task.moviesapp;

import android.content.Context;


public class AppApplication {
    public static Context context;

    public static Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
