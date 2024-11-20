package com.example.trainingCourses.domain.model

import com.example.trainingCourses.domain.useCases.dataRetrieval.GetCommentsCourseUseCase
import com.example.trainingCourses.domain.useCases.dataRetrieval.GetFavoriteCoursesUseCase
import com.example.trainingCourses.domain.useCases.filters.GetFilterValueUseCase
import com.example.trainingCourses.domain.useCases.filters.UpdateFiltersUseCase
import com.example.trainingCourses.domain.useCases.dataRetrieval.GetHomeCoursesUseCase
import com.example.trainingCourses.domain.useCases.filters.AddToFilterUseCase

sealed class UseCases {

    data class DataRetrieval(
        val getHomeCoursesUseCase: GetHomeCoursesUseCase,
        val getCommentsCourseUseCase: GetCommentsCourseUseCase,
        val getFavoriteCoursesUseCase: GetFavoriteCoursesUseCase,
    ) : UseCases()

    data class Filters(
        val addToFilterUseCase: AddToFilterUseCase,
        val getFilterValueUseCase: GetFilterValueUseCase,
        val updateFiltersUseCase: UpdateFiltersUseCase,
    ) : UseCases()
}
