package com.example.trainingCourses.domain.api

import com.example.trainingCourses.domain.model.CommentsCourseResponse
import com.example.trainingCourses.domain.model.CourseDetailsResponse
import com.example.trainingCourses.domain.model.CourseReviewsResponse
import com.example.trainingCourses.domain.model.SearchResultsResponse
import com.example.trainingCourses.domain.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val SEARCH_RESULTS_ENDPOINT = "search-results"
        const val COURSES_ENDPOINT = "courses"
        const val TYPE_QUERY_PARAM = "type"
        const val ORDER_QUERY_PARAM = "order"
        const val PAGE_QUERY_PARAM = "page"
        const val COURSE_LISTS_QUERY_PARAM = "course_lists[]"
        const val DIFFICULTY_QUERY_PARAM = "difficulty[]"
        const val IS_PAID_QUERY_PARAM = "is_paid"
        const val COURSE_REVIEWS_ENDPOINT = "course-reviews"
        const val COURSE_ID_QUERY_PARAM = "course"
    }

    @GET(SEARCH_RESULTS_ENDPOINT)
    suspend fun searchCourses(
        @Query(TYPE_QUERY_PARAM) type: String = "course",
        @Query(ORDER_QUERY_PARAM) order: String = "popularity",
        @Query(PAGE_QUERY_PARAM) page: Int = 1,
        @Query(COURSE_LISTS_QUERY_PARAM) courseLists: Int? = null,
        @Query(DIFFICULTY_QUERY_PARAM) difficulty: String? = null,
        @Query(IS_PAID_QUERY_PARAM) isPaid: Boolean? = null
    ): SearchResultsResponse

    @GET(COURSES_ENDPOINT)
    suspend fun getCoursesByIds(@Query("ids[]") courseIds: List<Int>): CourseDetailsResponse

    @GET(COURSE_REVIEWS_ENDPOINT)
    suspend fun getCourseReviews(@Query(COURSE_ID_QUERY_PARAM) courseId: Int): CourseReviewsResponse

    @GET(COURSE_REVIEWS_ENDPOINT)
    suspend fun getCommentsCourse(
        @Query(PAGE_QUERY_PARAM) page: Int = 1,
        @Query(COURSE_ID_QUERY_PARAM) courseId: Int
    ): CommentsCourseResponse

    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: Int): UserResponse
}
