package com.kyleriedemann.hiphopairhorn;

import android.app.Application;

import timber.log.Timber;

public class AirHornApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
    }
}
