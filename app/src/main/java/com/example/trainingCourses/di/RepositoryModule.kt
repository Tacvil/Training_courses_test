package com.example.trainingCourses.di

import com.example.trainingCourses.data.datasource.CoursesRemoteDataSource
import com.example.trainingCourses.data.repository.CoursesRepositoryImpl
import com.example.trainingCourses.domain.repository.CoursesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideCoursesRepository(remoteCourseDataSource: CoursesRemoteDataSource): CoursesRepository = CoursesRepositoryImpl(remoteCourseDataSource)
}