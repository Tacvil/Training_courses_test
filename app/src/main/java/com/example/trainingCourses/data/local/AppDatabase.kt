package com.example.trainingCourses.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.trainingCourses.data.local.entity.CourseEntity
import com.example.trainingCourses.data.local.entity.FavoriteCourseEntity

@Database(entities = [CourseEntity::class, FavoriteCourseEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coursesDao(): CoursesDao
    abstract fun favoriteCoursesDao(): FavoriteCoursesDao
}