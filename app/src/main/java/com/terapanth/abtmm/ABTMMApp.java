package com.terapanth.abtmm;

import android.app.Application;
import android.content.Context;


public class ABTMMApp extends Application {

    private static ABTMMApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static ABTMMApp getInstance(){
        return mInstance;
    }

    public Context getAppContext(){
        return mInstance.getApplicationContext();
    }
}
