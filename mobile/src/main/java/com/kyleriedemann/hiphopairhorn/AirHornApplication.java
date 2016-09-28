package com.kyleriedemann.hiphopairhorn;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class AirHornApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        Fabric.with(this, new Answers());

        Timber.plant(new Timber.DebugTree());
    }
}
