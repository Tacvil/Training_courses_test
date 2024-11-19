package com.example.trainingCourses.data.datasource

import com.example.trainingCourses.domain.api.ApiService
import com.example.trainingCourses.domain.api.ApiService.Companion.COURSE_LISTS_QUERY_PARAM
import com.example.trainingCourses.domain.api.ApiService.Companion.DIFFICULTY_QUERY_PARAM
import com.example.trainingCourses.domain.api.ApiService.Companion.IS_PAID_QUERY_PARAM
import com.example.trainingCourses.domain.api.ApiService.Companion.ORDER_QUERY_PARAM
import com.example.trainingCourses.domain.api.CommentsCourseResponse
import com.example.trainingCourses.domain.api.CoursesResponse
import com.example.trainingCourses.presentation.utils.DataHelper.parseDateToMillis
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

        val searchResultsResponse = apiService.searchCourses(
            page = page,
            order = filter[ORDER_QUERY_PARAM].toString(),
            courseLists = filter[COURSE_LISTS_QUERY_PARAM]?.takeIf { it.isNotBlank() }?.toInt(),
            difficulty = filter[DIFFICULTY_QUERY_PARAM]?.takeIf { it.isNotBlank() },
            isPaid = filter[IS_PAID_QUERY_PARAM]?.takeIf { it.isNotBlank() }
                ?.toBooleanStrictOrNull()
        )
        val originalCourseIds = searchResultsResponse.searchResults.map { it.course }

        val authorIds = searchResultsResponse.searchResults.mapNotNull { it.course_authors?.firstOrNull() }

        val coursesDeferred = async { apiService.getCoursesByIds(originalCourseIds) }
        val reviewsDeferred = originalCourseIds.map { courseId ->
            async {
                try {
                    apiService.getCourseReviews(courseId)
                } catch (e: Exception) {
                    Timber.e(e, "Error getting course reviews")
                    null
                }
            }
        }

        val usersDeferred = authorIds.map { authorId ->
            async {
                try {
                    apiService.getUser(authorId).users.firstOrNull()
                } catch (e: Exception) {
                    Timber.e(e, "Error getting user data")
                    null
                }
            }
        }

        val coursesByIds = coursesDeferred.await()
        val reviewsResponses = reviewsDeferred.awaitAll()
        val usersResponses = usersDeferred.awaitAll()

        // Создание маппинга ID курса -> объект курса
        val coursesMap = coursesByIds.courses.associateBy { it.id }
        // Создание маппинга ID курса -> отзывы о курсе
        val reviewsMap = reviewsResponses.filterNotNull()
            .flatMap { it.courseReviews } // Получаем все отзывы
            .groupBy { it.course } // Группируем отзывы по ID курса


        // Создание маппинга ID автора -> данные автора
        val usersMap = usersResponses.filterNotNull().associateBy { it.id }

        // Восстановление порядка курсов и обновление данных
        val orderedCourses = originalCourseIds.mapNotNull { courseId ->
            val course = coursesMap[courseId]
            if (course != null) {
                val reviewsForCourse = reviewsMap[courseId]
                val averageScore = reviewsForCourse?.sumOf { it.score }
                    ?.div(reviewsForCourse.size.toDouble()) ?: 0.0

                val formattedScore = if (averageScore.isNaN()) {
                    0.0
                } else {
                    kotlin.math.round(averageScore * 10) / 10.0
                }

                val authorId = searchResultsResponse.searchResults
                    .firstOrNull { it.course == courseId }
                    ?.course_authors
                    ?.firstOrNull()

                val user = usersMap[authorId]

                course.copy(
                    create_date = course.create_date?.let { parseDateToMillis(it).toString() },
                    score = formattedScore,
                    full_name = user?.full_name, // Получаем full_name из user
                    avatar = user?.avatar // Получаем avatar из user
                )
            } else {
                null
            }
        }

        CoursesResponse(searchResultsResponse.meta, orderedCourses)
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
