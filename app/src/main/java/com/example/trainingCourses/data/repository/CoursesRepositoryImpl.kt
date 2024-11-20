package com.example.trainingCourses.data.repository

import com.example.trainingCourses.data.datasource.CoursesRemoteDataSource
import com.example.trainingCourses.domain.model.CommentsCourseResponse
import com.example.trainingCourses.domain.model.CoursesResponse
import com.example.trainingCourses.domain.repository.CoursesRepository
import jakarta.inject.Inject

class CoursesRepositoryImpl @Inject constructor(
    private val remoteDataSource: CoursesRemoteDataSource
) : CoursesRepository {
    override suspend fun getCoursesFromApi(
        filter: MutableMap<String, String>,
        page: Int
    ): CoursesResponse {
        return remoteDataSource.getCoursesFromApi(filter, page)
    }

    override suspend fun getCommentsFromApi(courseId: Int, page: Int): CommentsCourseResponse {
        return remoteDataSource.getCommentsFromApi(courseId, page)
    }
}
