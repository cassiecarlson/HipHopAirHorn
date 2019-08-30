package com.kyleriedemann.hiphopairhorn

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log

import timber.log.Timber

class AirHornApplication : Application() {
    @SuppressLint("LogNotTimber")
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.i("Planted debug tree")
        Log.d("Timber", "Should have planted debug tree")
    }
}
