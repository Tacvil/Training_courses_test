package com.example.trainingCourses.domain.model

import com.example.trainingCourses.domain.api.ApiService.Companion.COURSE_REVIEWS_ENDPOINT
import com.google.gson.annotations.SerializedName

data class CommentsCourseResponse(
    val meta: Meta,
    @SerializedName(COURSE_REVIEWS_ENDPOINT)
    val comments: List<Comment>
)
