package com.example.trainingCourses.domain.useCases.dataRetrieval

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.trainingCourses.data.local.DatabaseModule
import com.example.trainingCourses.data.paging.CoursesPagingSource
import com.example.trainingCourses.domain.model.Courses
import com.example.trainingCourses.domain.repository.CoursesRepository
import com.example.trainingCourses.presentation.viewModel.MainViewModel.Companion.PAGE_SIZE
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetHomeCoursesUseCase
@Inject
constructor(
    private val coursesRepository: CoursesRepository,
) {
    operator fun invoke(filters: MutableMap<String, String>): Flow<PagingData<Courses>> =
        Pager(config = PagingConfig(pageSize = PAGE_SIZE)) {
            CoursesPagingSource(coursesRepository, DatabaseModule.provideCoursesDao(), filters)
        }.flow
}
