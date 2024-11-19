package com.example.trainingCourses.di

import android.app.Activity
import com.example.trainingCourses.domain.utils.ResourceStringProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ResourceProviderModule {
    @Provides
    fun provideResourceStringProvider(activity: Activity): ResourceStringProvider =
        if (activity is ResourceStringProvider) {
            activity
        } else {
            object : ResourceStringProvider {
                override fun getStringImpl(resId: Int): String = activity.getString(resId)
            }
        }
}
