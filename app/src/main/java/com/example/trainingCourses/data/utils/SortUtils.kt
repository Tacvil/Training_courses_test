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
    /*
        fun getSortOptionText(sortOptionId: String): String =
            when (sortOptionId) {
                SortOption.BY_NEWEST.id -> resourceStringProvider.getStringImpl(R.string.sort_by_newest)
                SortOption.BY_POPULARITY.id -> resourceStringProvider.getStringImpl(R.string.sort_by_popularity)
                SortOption.BY_PRICE_ASC.id -> resourceStringProvider.getStringImpl(R.string.sort_by_ascending_price)
                SortOption.BY_PRICE_DESC.id -> resourceStringProvider.getStringImpl(R.string.sort_by_descending_price)
                else -> sortOptionId
            }

        fun getCategory(item: String): String =
            when (item) {
                resourceStringProvider.getStringImpl(R.string.ad_car) -> SortOption.AD_CAR.id
                resourceStringProvider.getStringImpl(R.string.ad_pc) -> SortOption.AD_PC.id
                resourceStringProvider.getStringImpl(R.string.ad_smartphone) -> SortOption.AD_SMARTPHONE.id
                resourceStringProvider.getStringImpl(R.string.ad_dm) -> SortOption.AD_DM.id
                else -> item
            }

        fun getCategoryFromSortOption(item: String): String =
            when (item) {
                SortOption.AD_CAR.id -> resourceStringProvider.getStringImpl(R.string.ad_car)
                SortOption.AD_PC.id -> resourceStringProvider.getStringImpl(R.string.ad_pc)
                SortOption.AD_SMARTPHONE.id -> resourceStringProvider.getStringImpl(R.string.ad_smartphone)
                SortOption.AD_DM.id -> resourceStringProvider.getStringImpl(R.string.ad_dm)
                else -> item
            }

        fun getSendOption(item: String): String =
            when (item) {
                resourceStringProvider.getStringImpl(R.string.no_matter) -> SortOption.DELIVERY_UNSPECIFIED.id
                resourceStringProvider.getStringImpl(R.string.with_sending) -> SortOption.WITH_SEND.id
                resourceStringProvider.getStringImpl(R.string.without_sending) -> SortOption.WITHOUT_SEND.id
                else -> item
            }

        fun getSendOptionFromSortOption(item: String): String =
            when (item) {
                SortOption.DELIVERY_UNSPECIFIED.id -> resourceStringProvider.getStringImpl(R.string.no_matter)
                SortOption.WITH_SEND.id -> resourceStringProvider.getStringImpl(R.string.with_sending)
                SortOption.WITHOUT_SEND.id -> resourceStringProvider.getStringImpl(R.string.without_sending)
                else -> item
            }

        fun getCategoryFromItem(itemId: Int): String =
            when (itemId) {
                R.id.id_car -> SortOption.AD_CAR.id
                R.id.id_pc -> SortOption.AD_PC.id
                R.id.id_smartphone -> SortOption.AD_SMARTPHONE.id
                R.id.id_dm -> SortOption.AD_DM.id
                else -> ""
            }*/
}
