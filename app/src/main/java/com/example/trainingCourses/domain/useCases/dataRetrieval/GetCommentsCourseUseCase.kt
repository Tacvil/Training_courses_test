package com.example.trainingCourses.domain.useCases.dataRetrieval

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.trainingCourses.data.paging.CommentsCoursePagingSource
import com.example.trainingCourses.domain.model.Comment
import com.example.trainingCourses.domain.repository.CoursesRepository
import com.example.trainingCourses.presentation.viewModel.MainViewModel.Companion.PAGE_SIZE
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetCommentsCourseUseCase
@Inject
constructor(
    private val coursesRepository: CoursesRepository,
) {
    operator fun invoke(courseId: Int): Flow<PagingData<Comment>> =
        Pager(config = PagingConfig(pageSize = PAGE_SIZE)) {
            CommentsCoursePagingSource(coursesRepository, courseId)
        }.flow
}
