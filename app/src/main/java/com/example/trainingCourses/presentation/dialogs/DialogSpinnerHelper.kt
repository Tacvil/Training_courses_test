package com.example.trainingCourses.presentation.dialogs

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingCourses.R
import com.example.trainingCourses.presentation.adapters.RcViewDialogSpinnerAdapter
import jakarta.inject.Inject
import timber.log.Timber

object DialogSpinnerHelper {
    fun showDialogSpinner(
        context: Context,
        anchorView: View,
        spinnerItems: ArrayList<String>,
        targetTextView: TextView,
        onItemSelectedListener: RcViewDialogSpinnerAdapter.OnItemSelectedListener? = null
    ) {
        val spinnerLayout = LayoutInflater.from(context).inflate(R.layout.spinner_layout, null)
        val recyclerViewItems = spinnerLayout.findViewById<RecyclerView>(R.id.recycler_view_spinner_items).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = RcViewDialogSpinnerAdapter(targetTextView, onItemSelectedListener)
        }

        val popupWindow = PopupWindow(
            spinnerLayout,
            anchorView.width, // Ширина равна ширине anchorView
            ViewGroup.LayoutParams.WRAP_CONTENT // Высота подстраивается под содержимое
        ).apply {
            isOutsideTouchable = true
            setOnDismissListener {
                (recyclerViewItems.adapter as? RcViewDialogSpinnerAdapter)?.popupWindow = null
            }
        }

        (recyclerViewItems.adapter as? RcViewDialogSpinnerAdapter)?.popupWindow = popupWindow

        val yOffset = anchorView.resources.getDimensionPixelSize(R.dimen.popup_offset_y)
        popupWindow.showAsDropDown(anchorView, 0, yOffset)
        popupWindow.isFocusable = true // Устанавливаем фокус
        popupWindow.update() // Обновляем PopupWindow

        (recyclerViewItems.adapter as? RcViewDialogSpinnerAdapter)?.updateItems(spinnerItems)
    }
    }
