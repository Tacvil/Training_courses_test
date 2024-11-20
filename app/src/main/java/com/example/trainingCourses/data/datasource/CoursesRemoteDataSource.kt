package com.example.trainingCourses.data.datasource


import com.example.trainingCourses.data.utils.DataHelper.parseDateToMillis
import com.example.trainingCourses.domain.api.ApiService
import com.example.trainingCourses.domain.api.ApiService.Companion.COURSE_LISTS_QUERY_PARAM
import com.example.trainingCourses.domain.api.ApiService.Companion.DIFFICULTY_QUERY_PARAM
import com.example.trainingCourses.domain.api.ApiService.Companion.IS_PAID_QUERY_PARAM
import com.example.trainingCourses.domain.api.ApiService.Companion.ORDER_QUERY_PARAM
import com.example.trainingCourses.domain.model.CommentsCourseResponse
import com.example.trainingCourses.domain.model.CourseDetailsResponse
import com.example.trainingCourses.domain.model.CourseReview
import com.example.trainingCourses.domain.model.CourseReviewsResponse
import com.example.trainingCourses.domain.model.Courses
import com.example.trainingCourses.domain.model.CoursesResponse
import com.example.trainingCourses.domain.model.SearchResultsResponse
import com.example.trainingCourses.domain.model.UserResponse
import jakarta.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import timber.log.Timber

class CoursesRemoteDataSource
@Inject
constructor(
    private val apiService: ApiService

) {
    suspend fun getCoursesFromApi(
        filter: MutableMap<String, String>,
        page: Int,
    ): CoursesResponse = coroutineScope {
        val searchResultsResponse = fetchSearchResults(filter, page)
        val originalCourseIds = searchResultsResponse.searchResults.map { it.course }
        val authorIds = searchResultsResponse.searchResults.mapNotNull { it.course_authors?.firstOrNull() }

        val coursesByIds = fetchCoursesByIds(originalCourseIds)
        val reviewsResponses = fetchCourseReviews(originalCourseIds)
        val usersResponses = fetchUsersData(authorIds)

        val coursesMap = coursesByIds.courses.associateBy { it.id }
        val reviewsMap = buildReviewsMap(reviewsResponses)
        val usersMap = usersResponses.filterNotNull().associateBy { it.users.firstOrNull()?.id }

        val orderedCourses = assembleCourses(
            originalCourseIds,
            searchResultsResponse,
            coursesMap,
            reviewsMap,
            usersMap
        )

        CoursesResponse(searchResultsResponse.meta, orderedCourses)
    }

    private suspend fun fetchSearchResults(
        filter: MutableMap<String, String>,
        page: Int
    ): SearchResultsResponse {
        return apiService.searchCourses(
            page = page,
            order = filter[ORDER_QUERY_PARAM].toString(),
            courseLists = filter[COURSE_LISTS_QUERY_PARAM]?.takeIf { it.isNotBlank() }?.toInt(),
            difficulty = filter[DIFFICULTY_QUERY_PARAM]?.takeIf { it.isNotBlank() },
            isPaid = filter[IS_PAID_QUERY_PARAM]?.takeIf { it.isNotBlank() }?.toBooleanStrictOrNull()
        )
    }

    private suspend fun fetchCoursesByIds(originalCourseIds: List<Int>): CourseDetailsResponse {
        return apiService.getCoursesByIds(originalCourseIds)
    }

    private suspend fun fetchCourseReviews(originalCourseIds: List<Int>): List<CourseReviewsResponse?> = coroutineScope {
        originalCourseIds.map { courseId ->
            async {
                try {
                    apiService.getCourseReviews(courseId)
                } catch (e: Exception) {
                    Timber.e(e, "Error getting course reviews")
                    null
                }
            }
        }.awaitAll()
    }

    private suspend fun fetchUsersData(authorIds: List<Int?>): List<UserResponse?> = coroutineScope {
        authorIds.map { authorId ->
            async {
                try {
                    authorId?.let { apiService.getUser(it)}
                } catch (e: Exception) {
                    Timber.e(e, "Error getting user data")
                    null
                }
            }
        }.awaitAll()
    }

    private fun buildReviewsMap(reviewsResponses: List<CourseReviewsResponse?>): Map<Int, List<CourseReview>> {
        return reviewsResponses.filterNotNull()
            .flatMap { it.courseReviews }
            .groupBy { it.course }
    }

    private fun assembleCourses(
        originalCourseIds: List<Int>,
        searchResultsResponse: SearchResultsResponse,
        coursesMap: Map<Int?, Courses>,
        reviewsMap: Map<Int, List<CourseReview>>,
        usersMap: Map<Int?, UserResponse>
    ): List<Courses> {
        return originalCourseIds.mapNotNull { courseId ->
            val course = coursesMap[courseId]
            if (course != null) {
                val reviewsForCourse = reviewsMap[courseId]
                val averageScore = calculateAverageScore(reviewsForCourse)
                val formattedScore = formatScore(averageScore)

                val authorId = searchResultsResponse.searchResults
                    .firstOrNull { it.course == courseId }
                    ?.course_authors
                    ?.firstOrNull()

                val user = usersMap[authorId]

                course.copy(
                    create_date = course.create_date?.let { parseDateToMillis(it).toString() },
                    score = formattedScore,
                    full_name = user?.users?.firstOrNull()?.full_name,
                    avatar = user?.users?.firstOrNull()?.avatar
                )
            } else {
                null
            }
        }
    }

    private fun calculateAverageScore(reviewsForCourse: List<CourseReview>?): Double {
        return reviewsForCourse?.sumOf { it.score }
            ?.div(reviewsForCourse.size.toDouble()) ?: 0.0
    }

    private fun formatScore(averageScore: Double): Double {
        return if (averageScore.isNaN()) {
            0.0
        } else {
            kotlin.math.round(averageScore * 10) / 10.0
        }
    }


    suspend fun getCommentsFromApi(courseId: Int, page: Int): CommentsCourseResponse {
        val response = apiService.getCommentsCourse(page = page, courseId = courseId)

        val commentsWithFullName = response.comments.mapNotNull { comment ->
            try {
                val userResponse = apiService.getUser(comment.user.toInt())
                userResponse.users.firstOrNull()?.full_name?.let { comment.copy(full_name = it) }
            } catch (e: Exception) {
                Timber.e(e, "Error getting user full name for comment ${comment.id}")
                comment.copy(full_name = "")
            }
        }

        return response.copy(comments = commentsWithFullName)
    }
}
