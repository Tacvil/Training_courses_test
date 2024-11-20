package com.example.trainingCourses.domain.model

sealed class CourseUpdateEvent {
    data class FavUpdated(
        val courses: Courses,
    ) : CourseUpdateEvent()
}
