package com.example.trainingCourses.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.trainingCourses.data.local.FavoriteCoursesDao
import com.example.trainingCourses.domain.model.Courses
import com.example.trainingCourses.domain.utils.CourseMappers.toCourses

class FavoritesCoursePagingSource(
    private val favoriteCoursesDao: FavoriteCoursesDao
) : PagingSource<Int, Courses>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Courses> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            val offset = (page - 1) * pageSize

            val favoriteCourses = favoriteCoursesDao.getFavoriteCourses(pageSize, offset)
                .map { it.toCourses() }

            LoadResult.Page(
                data = favoriteCourses,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (favoriteCourses.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Courses>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
