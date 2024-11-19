package com.example.trainingCourses.domain.model

data class AppState(
    //val adEvent: AdUpdateEvent? = null,
    val filter: MutableMap<String, String> = mutableMapOf(),
)
