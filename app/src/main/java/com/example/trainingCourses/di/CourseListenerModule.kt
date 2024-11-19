package com.example.trainingCourses.di

import androidx.fragment.app.FragmentActivity
import com.example.trainingCourses.domain.ui.course.CourseItemClickListener
import com.example.trainingCourses.presentation.fragment.MainFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(ActivityComponent::class)
object CourseListenerModule {

    @Provides
    fun provideAdItemClickListener(activity: FragmentActivity): CourseItemClickListener = activity as CourseItemClickListener
}
