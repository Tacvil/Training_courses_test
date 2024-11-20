package com.example.trainingCourses.domain.useCases.dataRetrieval

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.trainingCourses.data.local.DatabaseModule
import com.example.trainingCourses.data.paging.FavoritesCoursePagingSource
import com.example.trainingCourses.domain.model.Courses
import com.example.trainingCourses.presentation.viewModel.MainViewModel.Companion.PAGE_SIZE
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetFavoriteCoursesUseCase
@Inject
constructor() {
    operator fun invoke(): Flow<PagingData<Courses>> =
        Pager(config = PagingConfig(pageSize = PAGE_SIZE)) {
            FavoritesCoursePagingSource(DatabaseModule.provideFavoriteCoursesDao())
        }.flow
}
