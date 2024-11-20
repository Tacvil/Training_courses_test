package com.example.trainingCourses.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val id_course: Int?,
    val title: String?,
    val summary: String?,
    val full_name: String?,
    val display_price: String?,
    val cover: String?,
    val score: Double?,
    val create_date: String?,
    val time_to_complete: String?,
    val canonical_url: String?,
    val description: String?,
    val avatar: String?,
    val isFavorite: Boolean
) {

}

