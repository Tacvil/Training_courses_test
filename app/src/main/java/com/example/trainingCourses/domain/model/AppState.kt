package com.example.trainingCourses.domain.model

data class AppState(
    val courseEvent: CourseUpdateEvent? = null,
    val filter: MutableMap<String, String> = mutableMapOf(),
)



