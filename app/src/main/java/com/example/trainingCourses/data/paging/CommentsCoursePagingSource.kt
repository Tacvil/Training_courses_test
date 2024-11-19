package com.example.trainingCourses.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.trainingCourses.domain.repository.CoursesRepository
import com.example.trainingCourses.presentation.fragment.Comment
import timber.log.Timber

class CommentsCoursePagingSource(
    private val coursesRepository: CoursesRepository,
    private val courseId: Int,
) : PagingSource<Int, Comment>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Comment> =
        try {
            val page = params.key ?: 1
            val response = coursesRepository.getCommentsFromApi(courseId, page)
            val commentsList = response.comments
            val nextKey = if (response.meta.has_next) page + 1 else null

            LoadResult.Page(commentsList, null, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    override fun getRefreshKey(state: PagingState<Int, Comment>): Int = 1
}
