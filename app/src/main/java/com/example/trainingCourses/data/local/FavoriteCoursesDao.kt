package com.example.trainingCourses.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.trainingCourses.data.local.entity.FavoriteCourseEntity

@Dao
interface FavoriteCoursesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(course: FavoriteCourseEntity)

    @Query("SELECT * FROM favorite_courses LIMIT :limit OFFSET :offset")
    suspend fun getFavoriteCourses(limit: Int, offset: Int): List<FavoriteCourseEntity>

    @Query("DELETE FROM favorite_courses WHERE id_course = :courseId")
    suspend fun delete(courseId: Int)

    @Query("SELECT * FROM favorite_courses WHERE id = :courseId")
    suspend fun getCourseById(courseId: Int): FavoriteCourseEntity?
}