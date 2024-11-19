package com.example.trainingCourses.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainingCourses.R
import com.example.trainingCourses.data.utils.SortOption
import com.example.trainingCourses.databinding.FragmentMainBinding
import com.example.trainingCourses.domain.api.ApiService.Companion.ORDER_QUERY_PARAM
import com.example.trainingCourses.domain.model.Courses
import com.example.trainingCourses.domain.ui.course.CourseItemClickListener
import com.example.trainingCourses.presentation.adapters.CoursesAdapter
import com.example.trainingCourses.presentation.dialogs.OrderByFilterDialogManager
import com.example.trainingCourses.presentation.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var orderByFilterDialogManager: OrderByFilterDialogManager

    @Inject
    lateinit var coursesAdapter: CoursesAdapter

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFilters()
        initUiComponents()
        setupSearchFunctionality()
        setupRecyclerView()
        observeCourses()
    }

    private fun initFilters() {
        val newFilters = mapOf(ORDER_QUERY_PARAM to SortOption.BY_POPULARITY.id)
        viewModel.updateFilters(newFilters)
    }

    private fun initUiComponents() {
        binding.imageViewFilter.setOnClickListener { showFilterFragment() }
    }

    private fun showFilterFragment() {
        findNavController().navigate(R.id.action_mainFragment_to_filterFragment)
    }

    private fun setupSearchFunctionality() {
        orderByFilterDialogManager.setupOrderByFilter(binding.textViewSortLabel)
    }

    private fun setupRecyclerView() {
        binding.recyclerViewCourses.adapter = coursesAdapter
        binding.recyclerViewCourses.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeCourses() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.homeCoursesData.collectLatest { pagingData ->
                coursesAdapter.submitData(pagingData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}