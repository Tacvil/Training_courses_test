package com.example.trainingCourses.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingCourses.R
import com.example.trainingCourses.presentation.utils.ItemDiffCallback

class RcViewDialogSpinnerAdapter(
    private var targetTextView: TextView,
    var onItemSelectedListener: OnItemSelectedListener? = null,
    var popupWindow: PopupWindow? = null
) : RecyclerView.Adapter<RcViewDialogSpinnerAdapter.SpinnerViewHolder>() {
    private val spinnerItems = ArrayList<String>()

    interface OnItemSelectedListener {
        fun onItemSelected(item: String)
    }

    class SpinnerViewHolder(
        itemView: View,
        private var textViewSelection: TextView,
        private var adapter: RcViewDialogSpinnerAdapter,
    ) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private var itemTitle = ""

        fun bind(item: String) {
            val spinnerTextView = itemView.findViewById<TextView>(R.id.text_view_spinner)
            spinnerTextView.text = item
            itemTitle = item

            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            textViewSelection.text = itemTitle
            adapter.dismissPopupWindow()
            adapter.onItemSelectedListener?.onItemSelected(itemTitle)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SpinnerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_spinner_list, parent, false)
        return SpinnerViewHolder(view, targetTextView, this)
    }

    override fun getItemCount(): Int = spinnerItems.size

    override fun onBindViewHolder(
        holder: SpinnerViewHolder,
        position: Int,
    ) {
        holder.bind(spinnerItems[position])
    }

    fun updateItems(newList: ArrayList<String>) {
        val diffCallback = ItemDiffCallback(spinnerItems, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        spinnerItems.clear()
        spinnerItems.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun dismissPopupWindow() {
        popupWindow?.dismiss()
    }
}
