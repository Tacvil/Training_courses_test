package com.example.trainingCourses.domain.dialog

import android.view.View
import android.widget.TextView
import com.example.trainingCourses.presentation.adapters.RcViewDialogSpinnerAdapter

interface OrderByFilterDialog {
    fun showSpinnerPopup(
        anchorView: View,
        list: ArrayList<String>,
        tvSelection: TextView,
        onItemSelectedListener: RcViewDialogSpinnerAdapter.OnItemSelectedListener?,
    )
}
