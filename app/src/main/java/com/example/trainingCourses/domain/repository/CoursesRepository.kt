package com.example.trainingCourses.domain.repository

import com.example.trainingCourses.domain.model.CommentsCourseResponse
import com.example.trainingCourses.domain.model.CoursesResponse

interface CoursesRepository {
    suspend fun getCoursesFromApi(filter: MutableMap<String, String>, page: Int): CoursesResponse

    suspend fun getCommentsFromApi(courseId: Int, page: Int): CommentsCourseResponse
}
