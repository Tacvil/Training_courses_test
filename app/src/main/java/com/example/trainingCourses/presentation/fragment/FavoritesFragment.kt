package com.example.trainingCourses.presentation.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.launch
import androidx.core.text.HtmlCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.trainingCourses.R
import com.example.trainingCourses.databinding.FragmentDescriptionBinding
import com.example.trainingCourses.databinding.FragmentMainBinding
import com.example.trainingCourses.domain.model.Courses
import com.example.trainingCourses.presentation.adapters.CommentsAdapter
import com.example.trainingCourses.presentation.adapters.CoursesAdapter
import com.example.trainingCourses.presentation.utils.TimeUtils
import com.example.trainingCourses.presentation.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.text.format
import kotlin.text.isLowerCase
import kotlin.text.replaceFirstChar
import kotlin.text.titlecase

@AndroidEntryPoint
class DescriptionFragment : Fragment() {
    private var _binding: FragmentDescriptionBinding? = null
    val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    private var canonicalUrl:String? = null
    private var courseId:Int? = null

    @Inject
    lateinit var commentsAdapter: CommentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCourseData()
        initUiComponents()
        setupRecyclerView()
        observeComments()
    }

    private fun observeComments() {
        lifecycleScope.launch {
            courseId?.let {
                viewModel.getComments(it).collectLatest { pagingData ->
                    commentsAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewComments.adapter = commentsAdapter
        binding.recyclerViewComments.layoutManager = LinearLayoutManager(requireContext())
    }

    @SuppressLint("NewApi")
    private fun setupCourseData() {
        val course = arguments?.getParcelable("course", Courses::class.java)
        course?.let {
            binding.textViewCourseTitle.text = course.title.toString()
            binding.textViewAuthorName.text = it.full_name.toString()
            // Загрузка картинки с помощью Glide
            binding.imageViewCourseCover.let { imageView ->
                Glide.with(this)
                    .load(it.cover)
                    .into(imageView)
            }

            binding.imageViewAuthorAvatar.let{ imageView ->
                Glide.with(this)
                    .load(it.avatar)
                    .into(imageView)
            }

            binding.textViewRatingValue.text = it.score.toString()
            binding.textViewCourseDate.text = it.create_date?.let { date ->
                TimeUtils.formatDate(date)
            }

            binding.textViewCourseDuration.text = it.time_to_complete?.let { duration ->
                formatDuration(duration)
            }

            canonicalUrl = it.canonical_url

            binding.textViewCourseDescription.text = it.description?.let { description ->
                HtmlCompat.fromHtml(
                    description,
                    HtmlCompat.FROM_HTML_MODE_COMPACT)
            }

            courseId = it.id

        }
    }

    private fun initUiComponents() {
        binding.imageViewBackButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.buttonStartCourse.setOnClickListener { openLinkPlatform() }
        binding.buttonGoToPlatform.setOnClickListener { openLinkPlatform() }
    }

    private fun openLinkPlatform() {
        canonicalUrl?.let { url ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } ?: run {
            Toast.makeText(requireContext(), "Ссылка не найдена", Toast.LENGTH_SHORT).show()
        }
    }

    private fun formatDuration(seconds: String): String {
        val hours = (seconds.toDouble() / 3600).toInt()
        return getString(R.string.course_duration, hours)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

data class Comment(
    val id: Int,
    val user: String,
    val score: String,
    val text: String,
    val full_name: String,
)