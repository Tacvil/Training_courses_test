package com.example.trainingCourses.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingCourses.databinding.ItemCommentsBinding
import com.example.trainingCourses.domain.model.Comment
import com.example.trainingCourses.presentation.utils.PagingCommentsDiffCallback
import jakarta.inject.Inject

class CommentsAdapter @Inject constructor() :
    PagingDataAdapter<Comment, CommentsAdapter.CommentViewHolder>(PagingCommentsDiffCallback) {

    class CommentViewHolder(private val binding: ItemCommentsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) {
            binding.textViewAuthorName.text = comment.full_name
            binding.textViewAuthorComment.text = comment.text
            binding.textViewRatingValue.text = comment.score
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding =
            ItemCommentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = getItem(position)
        if (comment != null) {
            holder.bind(comment)
        }
    }
}