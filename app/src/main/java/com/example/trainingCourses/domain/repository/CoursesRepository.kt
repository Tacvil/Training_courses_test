package com.example.trainingCourses.domain.repository

import android.media.tv.AdResponse
import com.example.trainingCourses.domain.api.CommentsCourseResponse

import com.example.trainingCourses.domain.api.CourseReviewsResponse
import com.example.trainingCourses.domain.api.CoursesResponse
import com.example.trainingCourses.domain.model.Courses
import com.example.trainingCourses.presentation.fragment.Comment

interface CoursesRepository {
    suspend fun getCoursesFromApi(filter: MutableMap<String, String>,page: Int): CoursesResponse

    suspend fun getCommentsFromApi(courseId: Int,page: Int): CommentsCourseResponse
}
