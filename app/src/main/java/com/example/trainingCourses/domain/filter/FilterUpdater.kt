package com.example.trainingCourses.domain.filter

interface FilterUpdater {
    fun addToFilter(
        key: String,
        value: String,
    )
}
