package com.example.trainingCourses.presentation.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trainingCourses.R
import com.example.trainingCourses.databinding.ItemCourseListBinding
import com.example.trainingCourses.domain.model.Courses
import com.example.trainingCourses.domain.ui.course.CourseItemClickListener
import com.example.trainingCourses.presentation.utils.PagingCourseDiffCallback
import com.example.trainingCourses.domain.utils.TimeUtils


abstract class BaseCourseAdapter<VH : BaseCourseAdapter.BaseCourseViewHolder>(
    private val courseItemClickListener: CourseItemClickListener,
) : PagingDataAdapter<Courses, VH>(PagingCourseDiffCallback) {
    abstract class BaseCourseViewHolder(
        private val binding: ItemCourseListBinding,
        private val adItemClickListener: CourseItemClickListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(courses: Courses) =
            with(binding) {
                textViewCourseTitle.text = courses.title
                textViewCourseDescription.text = courses.summary
                textViewCoursePrice.text =
                    if (courses.display_price == "-") "Бесплатно" else courses.display_price
                textViewCourseDate.text = courses.create_date?.let { TimeUtils.formatDate(it) }
                textViewScoreValue.text = courses.score.toString()

                Glide
                    .with(binding.root)
                    .load(courses.cover)
                    .into(imageViewCourseCover)

                val drawableResId = if (courses.isFavorite) {
                    R.drawable.ic_flag_fill_green
                } else {
                    R.drawable.ic_flag
                }
                imageViewCourseFlag.setImageResource(drawableResId)

                itemView.setOnClickListener {
                    adItemClickListener.onCourseClick(courses)
                }

                imageViewCourseFlag.setOnClickListener {
                    adItemClickListener.onFavClick(courses)
                }

            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): VH {
        val binding =
            ItemCourseListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return getViewHolder(binding, courseItemClickListener)
    }

    abstract fun getViewHolder(
        binding: ItemCourseListBinding,
        onCourseClick: CourseItemClickListener,
    ): VH

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(
        holder: VH,
        position: Int,
    ) {
        val courses = getItem(position)
        if (courses != null) {
            holder.bind(courses)
        }
    }
}
