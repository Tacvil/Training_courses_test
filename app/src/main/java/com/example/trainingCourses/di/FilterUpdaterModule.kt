package com.example.trainingCourses.di

import androidx.fragment.app.FragmentActivity
import com.example.trainingCourses.domain.filter.FilterUpdater
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object FilterUpdaterModule {
    @Provides
    fun provideFilterUpdater(activity: FragmentActivity): FilterUpdater = activity as FilterUpdater
}
