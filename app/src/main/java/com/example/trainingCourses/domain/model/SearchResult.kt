package com.example.trainingCourses.domain.model

data class SearchResult(
    val course: Int,
    val course_authors: List<Int>? = null
)
