package com.example.trainingCourses.presentation.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.trainingCourses.domain.model.Courses

object PagingCourseDiffCallback : DiffUtil.ItemCallback<Courses>() {
    override fun areItemsTheSame(
        oldItem: Courses,
        newItem: Courses,
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Courses,
        newItem: Courses,
    ): Boolean = oldItem == newItem
}
