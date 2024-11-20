package com.example.trainingCourses.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.trainingCourses.data.local.CoursesDao
import com.example.trainingCourses.domain.repository.CoursesRepository
import com.example.trainingCourses.domain.model.Courses
import com.example.trainingCourses.data.network.InternetConnectionManager
import com.example.trainingCourses.domain.utils.CourseMappers.toCourseEntity
import com.example.trainingCourses.domain.utils.CourseMappers.toCourses

class CoursesPagingSource(
    private val coursesRepository: CoursesRepository,
    private val coursesDao: CoursesDao,
    private val filters: MutableMap<String, String>,
) : PagingSource<Int, Courses>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Courses> =
        try {
            if (InternetConnectionManager.isNetworkAvailable()) {

                val page = params.key ?: 1
                val response = coursesRepository.getCoursesFromApi(filters, page)
                val coursesList = response.courses

                coursesDao.insertAll(coursesList.map { it.toCourseEntity() }) // Кешируем в Room

                LoadResult.Page(coursesList, null, if (response.meta.has_next) page + 1 else null)

            }else{

                val pagingSource = coursesDao.getAllCourses()
                val loadResult = pagingSource.load(params)

                val coursesList = (loadResult as? LoadResult.Page)?.data?.map { it.toCourses() }

                LoadResult.Page(
                    data = coursesList.orEmpty(),
                    prevKey = (loadResult as? LoadResult.Page)?.prevKey,
                    nextKey = (loadResult as? LoadResult.Page)?.nextKey
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    override fun getRefreshKey(state: PagingState<Int, Courses>): Int = 1
}
