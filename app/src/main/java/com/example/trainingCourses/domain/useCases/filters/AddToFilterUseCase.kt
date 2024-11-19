package com.example.trainingCourses.domain.useCases.filters

import jakarta.inject.Inject

class AddToFilterUseCase
    @Inject
    constructor() {
        operator fun invoke(
            currentFilters: MutableMap<String, String>,
            key: String,
            value: String,
        ): MutableMap<String, String> = currentFilters.toMutableMap().apply { this[key] = value }
    }
