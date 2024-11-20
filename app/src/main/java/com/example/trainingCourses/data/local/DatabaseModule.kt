package com.example.trainingCourses.di

import android.content.Context
import androidx.room.Room
import com.example.trainingCourses.data.local.AppDatabase
import com.example.trainingCourses.data.local.CoursesDao
import com.example.trainingCourses.domain.useCases.dataRetrieval.GetHomeCoursesUseCase
import dagger.hilt.components.SingletonComponent
import dagger.Module
import dagger.Provides
import dagger.assisted.AssistedFactory
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCoursesDao(appDatabase: AppDatabase): CoursesDao {
        return appDatabase.coursesDao()
    }
}