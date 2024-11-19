package com.example.trainingCourses.domain.utils

interface ResourceStringProvider {
    fun getStringImpl(resId: Int): String
}
