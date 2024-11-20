package com.example.trainingCourses.application

import android.app.Application
import com.example.trainingCourses.data.local.DatabaseModule
import com.example.trainingCourses.data.network.InternetConnectionManager
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        InternetConnectionManager.initialize(this)
        DatabaseModule.initialize(this)
        Timber.plant(Timber.DebugTree())
    }
}
