package com.example.trainingCourses.data.local

import android.content.Context
import androidx.room.Room

object DatabaseModule {

    private lateinit var appDatabase: AppDatabase

    fun initialize(context: Context) {
        appDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    fun provideCoursesDao(): CoursesDao {
        return appDatabase.coursesDao()
    }

    fun provideFavoriteCoursesDao(): FavoriteCoursesDao {
        return appDatabase.favoriteCoursesDao()
    }
}