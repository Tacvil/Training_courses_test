package com.example.trainingCourses.presentation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.trainingCourses.R
import com.example.trainingCourses.data.utils.SortOption
import com.example.trainingCourses.data.utils.SortUtils
import com.example.trainingCourses.databinding.FragmentFilterBinding
import com.example.trainingCourses.domain.api.ApiService.Companion.COURSE_LISTS_QUERY_PARAM
import com.example.trainingCourses.domain.api.ApiService.Companion.DIFFICULTY_QUERY_PARAM
import com.example.trainingCourses.domain.api.ApiService.Companion.IS_PAID_QUERY_PARAM
import com.example.trainingCourses.domain.model.Courses
import com.example.trainingCourses.presentation.adapters.RcViewDialogSpinnerAdapter
import com.example.trainingCourses.presentation.dialogs.DialogSpinnerHelper
import com.example.trainingCourses.presentation.viewModel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class FilterFragment
    @Inject
    constructor(
    ) : BottomSheetDialogFragment() {
        private val viewModel: MainViewModel by activityViewModels()
        private var _binding: FragmentFilterBinding? = null
        val binding get() = _binding!!

        @Inject
        lateinit var sortUtils: SortUtils

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
        ): View {
            _binding = FragmentFilterBinding.inflate(inflater, container, false)
            return binding.root
        }

        @SuppressLint("NewApi")
        override fun onViewCreated(
            view: View,
            savedInstanceState: Bundle?,
        ) {
            super.onViewCreated(view, savedInstanceState)

            val bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            getFilter()
            setupSelectors()
            onClickApplyFilters()
            onClickClear()
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

        private fun getFilter() = with(binding) {
                viewModel.getFilterValue(COURSE_LISTS_QUERY_PARAM)?.let { selectCategoryEditText.setText(sortUtils.getCategoryOptionFromSortOption(it)) }
                viewModel.getFilterValue(DIFFICULTY_QUERY_PARAM)?.let { selectDifficultEditText.setText(sortUtils.getDifficultOptionFromSortOption(it)) }
                viewModel.getFilterValue(IS_PAID_QUERY_PARAM)?.let { selectPriceEditText.setText(sortUtils.getPriceOptionFromSortOption(it)) }
            }

        private fun setupSelectors() =
            with(binding) {

                selectCategoryEditText.setOnClickListener {
                    val categoryOptionsList = arrayListOf(
                            getString(R.string.category_android_development),
                            getString(R.string.category_ux_ui_design),
                                getString(R.string.category_android),
                                getString(R.string.category_kotlin),
                                getString(R.string.category_mobile_development),
                                getString(R.string.category_app_development),
                        )
                    showSpinnerPopup(selectCategoryEditText, categoryOptionsList) {
                        selectCategoryEditText.setText(it)
                    }
                }

                selectDifficultEditText.setOnClickListener {
                    val categoryDifficultList = arrayListOf(
                        getString(R.string.difficulty_easy),
                        getString(R.string.difficulty_medium),
                        getString(R.string.difficulty_hard),
                    )
                    showSpinnerPopup(selectDifficultEditText, categoryDifficultList) {
                        selectDifficultEditText.setText(it)
                    }
                }
            }

        private fun showSpinnerPopup(
            textView: TextView,
            items: ArrayList<String>,
            onItemSelected: (String) -> Unit,
        ) {
            DialogSpinnerHelper.showDialogSpinner(
                requireContext(),
                textView,
                items,
                textView,
                object : RcViewDialogSpinnerAdapter.OnItemSelectedListener {
                    override fun onItemSelected(item: String) {
                        onItemSelected(item)
                    }
                },
            )
        }

        private fun onClickApplyFilters() =
            with(binding) {
                applyFilterButton.setOnClickListener {
                    createFilter()
                    dismiss()
                }
            }

        private fun onClickClear() =
            with(binding) {
                clearFilterButton.setOnClickListener {
                    selectCategoryEditText.setText(EMPTY_STRING)
                    selectDifficultEditText.setText(EMPTY_STRING)
                    selectPriceEditText.setText(EMPTY_STRING)
                    selectCategoryTextInputLayout.hint = getString(R.string.filter_hint_category)
                    selectDifficultTextInputLayout.hint = getString(R.string.filter_hint_difficult)
                    selectPriceTextInputLayout.hint = getString(R.string.filter_hint_price)
                }
            }

    private fun createFilter() {
        with(binding) {
            val filters = mutableMapOf<String, String>()

            selectCategoryEditText.text.toString().let {
                filters[COURSE_LISTS_QUERY_PARAM] = sortUtils.getCategoryOption(it)
            }
            selectDifficultEditText.text.toString().let {
                filters[DIFFICULTY_QUERY_PARAM] = sortUtils.getDifficultOption(it)
            }
            selectPriceEditText.text.toString().let {
                if (it != getString(R.string.price_no_matter)) filters[IS_PAID_QUERY_PARAM] =  sortUtils.getPriceOption(it)
            }

            viewModel.updateFilters(filters)
        }
    }

        companion object {
            const val FILTER_FRAGMENT_TAG = "FilterFragment"
            const val EMPTY_STRING = ""
        }
    }
