package com.example.trainingCourses.di

import androidx.fragment.app.FragmentActivity
import com.example.trainingCourses.domain.ui.adapters.AppStateListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object AppStateListenerModule {
    @Provides
    fun provideAppStateListener(activity: FragmentActivity): AppStateListener = activity as AppStateListener

}