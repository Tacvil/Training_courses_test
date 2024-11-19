package com.example.trainingCourses.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.trainingCourses.domain.repository.CoursesRepository
import com.example.trainingCourses.domain.model.Courses
import timber.log.Timber

class CoursesPagingSource(
    private val coursesRepository: CoursesRepository,
    private val filters: MutableMap<String, String>,
) : PagingSource<Int, Courses>() {

    init {
        Timber.d("initPagingSource")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Courses> =
        try {
            Timber.d("NOVSDSDSDSDSDSDSDSDSDSDDSD")
            val page = params.key ?: 1
            val response = coursesRepository.getCoursesFromApi(filters, page)
            val coursesList = response.courses // Извлекаем список курсов из CoursesResponse
            Timber.d("nextKey: ${response.meta.has_next}")
            val nextKey = if (response.meta.has_next) page + 1 else null
            Timber.d("nextKey: $nextKey")

            LoadResult.Page(coursesList, null, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    override fun getRefreshKey(state: PagingState<Int, Courses>): Int = 1
}
