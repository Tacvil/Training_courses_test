package com.example.trainingCourses.data.utils

import com.example.trainingCourses.R
import com.example.trainingCourses.domain.utils.ResourceStringProvider
import jakarta.inject.Inject

class SortUtils
@Inject
constructor(
    private val resourceStringProvider: ResourceStringProvider,
) {
    fun getSortOption(item: String): String =
        when (item) {
            resourceStringProvider.getStringImpl(R.string.order_by_rating) -> SortOption.BY_RATING.id
            resourceStringProvider.getStringImpl(R.string.order_by_popularity) -> SortOption.BY_POPULARITY.id
            resourceStringProvider.getStringImpl(R.string.order_by_date_added) -> SortOption.BY_DATE_ADDED.id
            else -> item
        }

    fun getCategoryOption(item: String): String =
        when (item) {
            resourceStringProvider.getStringImpl(R.string.category_android_development) -> SortOption.ANDROID_DEV.id
            resourceStringProvider.getStringImpl(R.string.category_ux_ui_design) -> SortOption.UX_UI_DESIGN.id
            resourceStringProvider.getStringImpl(R.string.category_android) -> SortOption.ANDROID.id
            resourceStringProvider.getStringImpl(R.string.category_kotlin) -> SortOption.KOTLIN.id
            resourceStringProvider.getStringImpl(R.string.category_mobile_development) -> SortOption.MOBILE_DEV.id
            resourceStringProvider.getStringImpl(R.string.category_app_development) -> SortOption.APP_DEV.id
            else -> item
        }

    fun getDifficultOption(item: String): String =
        when (item) {
            resourceStringProvider.getStringImpl(R.string.difficulty_easy) -> SortOption.EASY.id
            resourceStringProvider.getStringImpl(R.string.difficulty_medium) -> SortOption.MEDIUM.id
            resourceStringProvider.getStringImpl(R.string.difficulty_hard) -> SortOption.HARD.id
            else -> item
        }

    fun getPriceOption(item: String): String =
        when (item) {
            resourceStringProvider.getStringImpl(R.string.price_free) -> SortOption.IS_PAID_FALSE.id
            resourceStringProvider.getStringImpl(R.string.price_paid) -> SortOption.IS_PAID_TRUE.id
            else -> item
        }

    fun getCategoryOptionFromSortOption(item: String): String =
        when (item) {
            SortOption.ANDROID_DEV.id -> resourceStringProvider.getStringImpl(R.string.category_android_development)
            SortOption.UX_UI_DESIGN.id -> resourceStringProvider.getStringImpl(R.string.category_ux_ui_design)
            SortOption.ANDROID.id -> resourceStringProvider.getStringImpl(R.string.category_android)
            SortOption.KOTLIN.id -> resourceStringProvider.getStringImpl(R.string.category_kotlin)
            SortOption.MOBILE_DEV.id -> resourceStringProvider.getStringImpl(R.string.category_mobile_development)
            SortOption.APP_DEV.id -> resourceStringProvider.getStringImpl(R.string.category_app_development)
            else -> item
        }

    fun getDifficultOptionFromSortOption(item: String): String =
        when (item) {
            SortOption.EASY.id -> resourceStringProvider.getStringImpl(R.string.difficulty_easy)
            SortOption.MEDIUM.id -> resourceStringProvider.getStringImpl(R.string.difficulty_medium)
            SortOption.HARD.id -> resourceStringProvider.getStringImpl(R.string.difficulty_hard)
            else -> item
        }

    fun getPriceOptionFromSortOption(item: String): String =
        when (item) {
            SortOption.IS_PAID_FALSE.id -> resourceStringProvider.getStringImpl(R.string.price_free)
            SortOption.IS_PAID_TRUE.id -> resourceStringProvider.getStringImpl(R.string.price_paid)
            else -> item
        }
}
