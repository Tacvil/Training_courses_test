package com.example.trainingCourses.presentation.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.trainingCourses.R
import com.example.trainingCourses.databinding.FragmentDescriptionBinding
import com.example.trainingCourses.domain.model.Courses
import com.example.trainingCourses.presentation.activities.MainActivity.Companion.COURSE_BUNDLE
import com.example.trainingCourses.presentation.adapters.CommentsAdapter
import com.example.trainingCourses.domain.utils.TimeUtils
import com.example.trainingCourses.presentation.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DescriptionFragment : Fragment() {
    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    private var canonicalUrl: String? = null
    private var courseId: Int? = null

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
        val course = arguments?.getParcelable(COURSE_BUNDLE, Courses::class.java)
        course?.let { courses ->
            binding.textViewCourseTitle.text = course.title.toString()
            binding.textViewAuthorName.text = courses.full_name.toString()

            binding.imageViewCourseCover.let { imageView ->
                Glide.with(this)
                    .load(courses.cover)
                    .into(imageView)
            }

            binding.imageViewAuthorAvatar.let { imageView ->
                Glide.with(this)
                    .load(courses.avatar)
                    .into(imageView)
            }

            binding.textViewRatingValue.text = courses.score.toString()
            binding.textViewCourseDate.text = courses.create_date?.let { date ->
                TimeUtils.formatDate(date)
            }

            binding.textViewCourseDuration.text = courses.time_to_complete?.let { duration ->
                formatDuration(duration)
            }

            canonicalUrl = courses.canonical_url

            binding.textViewCourseDescription.text = courses.description?.let { description ->
                HtmlCompat.fromHtml(
                    description,
                    HtmlCompat.FROM_HTML_MODE_COMPACT
                )
            }

            if (courses.isFavorite){
                binding.imageViewFlag.setImageResource(R.drawable.ic_flag_fill_green)
            }

            binding.imageViewFlag.setOnClickListener { addToFav(courses) }

            courseId = courses.id

        }
    }

    private fun initUiComponents() {
        binding.imageViewBackButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.buttonStartCourse.setOnClickListener { openLinkPlatform() }
        binding.buttonGoToPlatform.setOnClickListener { openLinkPlatform() }

    }

    private fun addToFav(courses: Courses) {
        if (courses.isFavorite){
            binding.imageViewFlag.setImageResource(R.drawable.ic_flag2)
        }else{
            binding.imageViewFlag.setImageResource(R.drawable.ic_flag_fill_green)
        }
        lifecycleScope.launch {
            viewModel.toggleFavorite(courses)
        }
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