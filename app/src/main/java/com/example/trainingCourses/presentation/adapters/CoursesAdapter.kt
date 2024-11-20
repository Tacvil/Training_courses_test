package com.example.trainingCourses.presentation.adapters

import com.example.trainingCourses.domain.ui.adapters.AppStateListener
import com.example.trainingCourses.databinding.ItemCourseListBinding
import com.example.trainingCourses.domain.model.CourseUpdateEvent
import com.example.trainingCourses.domain.model.Courses
import com.example.trainingCourses.domain.ui.course.CourseItemClickListener
import jakarta.inject.Inject

class CoursesAdapter @Inject constructor(
    courseItemClickListener: CourseItemClickListener,
    appStateListener: AppStateListener,
) : BaseCourseAdapter<CoursesAdapter.CourseViewHolder>(courseItemClickListener) {

    init {
        appStateListener.onAppStateEvent { courseEvent ->
            when (courseEvent) {
                is CourseUpdateEvent.FavUpdated -> updateFav(courseEvent.courses)
            }
        }
    }

    private fun updateFav(courses: Courses) {
        val position = snapshot().items.indexOfFirst { it.id == courses.id }
        if (position != -1) {
            val courseToUpdate = snapshot().items[position]
            courseToUpdate.isFavorite = courses.isFavorite
            notifyItemChanged(position)
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