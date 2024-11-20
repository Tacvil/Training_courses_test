package com.example.trainingCourses.presentation.dialogs

import android.widget.TextView
import android.widget.Toast
import com.example.trainingCourses.R
import com.example.trainingCourses.data.utils.SortUtils
import com.example.trainingCourses.domain.api.ApiService.Companion.ORDER_QUERY_PARAM
import com.example.trainingCourses.domain.dialog.OrderByFilterDialog
import com.example.trainingCourses.domain.filter.FilterUpdater
import com.example.trainingCourses.domain.utils.ResourceStringProvider
import com.example.trainingCourses.presentation.adapters.RcViewDialogSpinnerAdapter
import jakarta.inject.Inject

class OrderByFilterDialogManager
@Inject
constructor(
    private val resourceStringProvider: ResourceStringProvider,
    private val filterUpdater: FilterUpdater,
    private val sortUtils: SortUtils,
    private val orderDialog: OrderByFilterDialog,
) {
    fun setupOrderByFilter(autoComplete: TextView) {
        autoComplete.setOnClickListener {
            val listVariant: ArrayList<String> = getFilterOptions()
            val onItemSelectedListener =
                object : RcViewDialogSpinnerAdapter.OnItemSelectedListener {
                    override fun onItemSelected(item: String) {
                        filterUpdater.addToFilter(ORDER_QUERY_PARAM, sortUtils.getSortOption(item))
                    }
                }

            orderDialog.showSpinnerPopup(
                autoComplete,
                listVariant,
                autoComplete,
                onItemSelectedListener,
            )
        }
    }

    private fun getFilterOptions(): ArrayList<String> =
        arrayListOf(
            resourceStringProvider.getStringImpl(R.string.order_by_popularity),
            resourceStringProvider.getStringImpl(R.string.order_by_rating),
            resourceStringProvider.getStringImpl(R.string.order_by_date_added),
        )
}
