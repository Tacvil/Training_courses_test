package com.example.trainingCourses.di

import com.example.trainingCourses.presentation.fragment.MainFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object MainFragmentModule {

    @Provides
    fun provideMainFragment(): MainFragment {
        return MainFragment()
    }
}