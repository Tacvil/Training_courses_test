package com.example.trainingCourses.presentation.adapters

import com.example.trainingCourses.domain.ui.adapters.AppStateListener
import com.example.trainingCourses.databinding.ItemCourseListBinding
import com.example.trainingCourses.domain.model.CourseUpdateEvent
import com.example.trainingCourses.domain.ui.course.CourseItemClickListener
import jakarta.inject.Inject

class FavoritesAdapter @Inject constructor(
    courseItemClickListener: CourseItemClickListener,
    appStateListener: AppStateListener,
) : BaseCourseAdapter<FavoritesAdapter.CourseViewHolder>(courseItemClickListener) {

    init {
        appStateListener.onAppStateEvent { courseEvent ->
            when (courseEvent) {
                is CourseUpdateEvent.FavUpdated -> refresh()
            }
        }
    }

    class CourseViewHolder(
        binding: ItemCourseListBinding,
        courseItemClickListener: CourseItemClickListener,
    ) : BaseCourseViewHolder(binding, courseItemClickListener)

    override fun getViewHolder(
        binding: ItemCourseListBinding,
        onCourseClick: CourseItemClickListener,
    ): CourseViewHolder = CourseViewHolder(binding, onCourseClick)
}