package com.example.trainingCourses.domain.ui.adapters

import com.example.trainingCourses.domain.model.CourseUpdateEvent


interface AppStateListener {
    fun onAppStateEvent(courseEvent: (CourseUpdateEvent) -> Unit)
}
