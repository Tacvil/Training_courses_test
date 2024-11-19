package com.example.trainingCourses.presentation.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.trainingCourses.domain.model.Courses
import com.example.trainingCourses.presentation.fragment.Comment

object PagingCommentsDiffCallback : DiffUtil.ItemCallback<Comment>() {
    override fun areItemsTheSame(
        oldItem: Comment,
        newItem: Comment,
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Comment,
        newItem: Comment,
    ): Boolean = oldItem == newItem
}
