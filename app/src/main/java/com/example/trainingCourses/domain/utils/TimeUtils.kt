package com.example.trainingCourses.presentation.utils

import android.annotation.SuppressLint
import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.util.Locale

object TimeUtils {

    @SuppressLint("NewApi")
    private val formatter = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))

    @SuppressLint("NewApi")
    fun formatDate(timeMillis: String): String {
        val c = Calendar.getInstance()
        c.timeInMillis = timeMillis.toLong()
        val formattedDate = formatter.format(c.time)
        return formattedDate.split(" ").joinToString(" ") { it ->
            it.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }
        }
    }

}