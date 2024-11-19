package com.example.trainingCourses.domain.api

import com.example.trainingCourses.domain.api.ApiService.Companion.COURSE_REVIEWS_ENDPOINT
import com.example.trainingCourses.domain.model.Courses
import com.example.trainingCourses.presentation.fragment.Comment
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

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
        @Query(TYPE_QUERY_PARAM) type: String = "course", // не будем
        @Query(ORDER_QUERY_PARAM) order: String = "popularity", //будем менять ORDER_QUERY_PARAM to BY_DATE_ADDED BY_POPULARITY BY_RATING
        @Query(PAGE_QUERY_PARAM) page: Int = 1,
        @Query(COURSE_LISTS_QUERY_PARAM) courseLists: Int? = null, //будем менять  COURSE_LISTS_QUERY_PARAM enum Android.id"1" etc
        @Query(DIFFICULTY_QUERY_PARAM) difficulty: String? = null, //будем менять DIFFICULTY_QUERY_PARAM  enum Easy.id"easy" etc
        @Query(IS_PAID_QUERY_PARAM) isPaid: Boolean? = null //будем менять IS_PAID_QUERY_PARAM  enum IS_PAID.id"true"/ false
    ): SearchResultsResponse

    @GET("$COURSES_ENDPOINT/{courseId}")
    suspend fun getCourseDetails(@Path("courseId") courseId: Int): CourseDetailsResponse

    @GET(COURSES_ENDPOINT)
    suspend fun getCoursesByIds(@Query("ids[]") courseIds: List<Int>): CourseDetailsResponse

    @GET(COURSE_REVIEWS_ENDPOINT)
    suspend fun getCourseReviews(@Query(COURSE_ID_QUERY_PARAM) courseId: Int): CourseReviewsResponse

    @GET(COURSE_REVIEWS_ENDPOINT)
    suspend fun getCommentsCourse(
        @Query(PAGE_QUERY_PARAM) page: Int = 1,
        @Query(COURSE_ID_QUERY_PARAM) courseId: Int): CommentsCourseResponse

    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: Int): UserResponse
}

data class UserResponse(
    val users: List<User>
)

data class User(
    val id: Int,
    val full_name: String,
    val avatar: String
)

data class SearchResultsResponse(
    val meta: Meta,
    @SerializedName("search-results")
    val searchResults: List<SearchResult>
)

data class SearchResult(
    val course: Int,
    val course_authors: List<Int>? = null
)

data class CourseDetailsResponse(
    val meta: Meta,
    val courses: List<Courses>
)

data class CoursesResponse(
    val meta: Meta,
    val courses: List<Courses>
)

data class Meta(
    val page: Int,
    val has_next: Boolean,
    val has_previous: Boolean
)

data class CourseReviewsResponse(
    val meta: Meta,
    @SerializedName(COURSE_REVIEWS_ENDPOINT)
    val courseReviews: List<CourseReview>
)

data class CommentsCourseResponse(
    val meta: Meta,
    @SerializedName(COURSE_REVIEWS_ENDPOINT)
    val comments: List<Comment>
)

data class CourseReview(
    val score: Int,
    val course: Int // Add this property
)