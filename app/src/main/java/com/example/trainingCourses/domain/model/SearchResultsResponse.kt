package com.example.trainingCourses.domain.model

import com.example.trainingCourses.domain.api.ApiService.Companion.SEARCH_RESULTS_ENDPOINT
import com.google.gson.annotations.SerializedName

data class SearchResultsResponse(
    val meta: Meta,
    @SerializedName(SEARCH_RESULTS_ENDPOINT)
    val searchResults: List<SearchResult>
)
