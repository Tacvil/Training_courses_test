package com.example.trainingCourses.di

import androidx.fragment.app.FragmentActivity
import com.example.trainingCourses.domain.dialog.OrderByFilterDialog
import com.example.trainingCourses.presentation.fragment.MainFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object OrderByFilterDialogManagerModule {
    @Provides
    fun provideOrderByFilterDialog(activity: FragmentActivity): OrderByFilterDialog = activity as OrderByFilterDialog
}
