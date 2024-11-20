package com.example.trainingCourses.presentation.adapters

import com.example.trainingCourses.databinding.ItemCourseListBinding
import com.example.trainingCourses.domain.model.Courses
import com.example.trainingCourses.domain.ui.course.CourseItemClickListener
import jakarta.inject.Inject

class CoursesAdapter @Inject constructor(
    courseItemClickListener: CourseItemClickListener,
) : BaseCourseAdapter<CoursesAdapter.CourseViewHolder>(courseItemClickListener) {


    class CourseViewHolder(
        binding: ItemCourseListBinding,
        courseItemClickListener: CourseItemClickListener,
    ) : BaseCourseViewHolder(binding, courseItemClickListener)

    override fun getViewHolder(
        binding: ItemCourseListBinding,
        onCourseClick: CourseItemClickListener,
    ): CourseViewHolder = CourseViewHolder(binding, onCourseClick)
}