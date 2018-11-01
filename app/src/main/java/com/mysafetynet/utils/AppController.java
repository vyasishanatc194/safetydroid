package com.mysafetynet.utils;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.google.firebase.FirebaseApp;

public class AppController extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseApp.initializeApp(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }
}
