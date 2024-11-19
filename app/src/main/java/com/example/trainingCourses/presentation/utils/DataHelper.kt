package com.example.trainingCourses.presentation.utils

import android.annotation.SuppressLint
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DataHelper {
    @SuppressLint("NewApi")
    fun parseDateToMillis(dateString: String): Long {
        val formatter = DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.of("UTC"))
        val instant = Instant.from(formatter.parse(dateString))
        return instant.toEpochMilli()
    }
}