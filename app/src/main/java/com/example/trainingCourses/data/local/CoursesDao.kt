package com.example.trainingCourses.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import com.example.trainingCourses.data.local.entity.CourseEntity
import jakarta.inject.Singleton

@Dao
interface CoursesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(courses: List<CourseEntity>)

    @Query("SELECT * FROM courses ORDER BY id ASC")
    fun getAllCourses(): PagingSource<Int, CourseEntity>

    @Query("DELETE FROM courses")
    suspend fun deleteAll()
}

