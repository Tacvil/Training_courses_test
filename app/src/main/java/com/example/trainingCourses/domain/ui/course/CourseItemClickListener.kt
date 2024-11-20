package com.example.trainingCourses.domain.ui.course

import com.example.trainingCourses.domain.model.Courses

interface CourseItemClickListener {
    fun onCourseClick(courses: Courses)

    fun onFavClick(course: Courses)
}
