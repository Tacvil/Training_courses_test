package com.example.trainingCourses.domain.model

data class Comment(
    val id: Int,
    val user: String,
    val score: String,
    val text: String,
    val full_name: String,
)
