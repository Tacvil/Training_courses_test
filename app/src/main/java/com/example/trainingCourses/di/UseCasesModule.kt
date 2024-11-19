package com.example.trainingCourses.di

import com.example.trainingCourses.domain.model.UseCases
import com.example.trainingCourses.domain.useCases.dataRetrieval.GetCommentsCourseUseCase
import com.example.trainingCourses.domain.useCases.dataRetrieval.GetHomeCoursesUseCase
import com.example.trainingCourses.domain.useCases.filters.AddToFilterUseCase
import com.example.trainingCourses.domain.useCases.filters.GetFilterValueUseCase
import com.example.trainingCourses.domain.useCases.filters.UpdateFiltersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    fun provideDataRetrievalUseCases(
        getHomeAdsUseCase: GetHomeCoursesUseCase,
        getCommentsCourseUseCase: GetCommentsCourseUseCase,
    ): UseCases.DataRetrieval = UseCases.DataRetrieval(getHomeAdsUseCase, getCommentsCourseUseCase)


    @Provides
    fun provideFiltersUseCases(
        addToFilterUseCase: AddToFilterUseCase,
        getFilterValueUseCase: GetFilterValueUseCase,
        updateFiltersUseCase: UpdateFiltersUseCase,
    ): UseCases.Filters =
        UseCases.Filters(addToFilterUseCase, getFilterValueUseCase, updateFiltersUseCase)

}
