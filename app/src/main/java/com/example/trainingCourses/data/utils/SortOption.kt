package com.example.trainingCourses.data.utils

enum class SortOption(
    val id: String,
) {
    BY_DATE_ADDED("create_date"),
    BY_POPULARITY("popularity"),
    BY_RATING("rating"),

    ANDROID_DEV("217"),
    UX_UI_DESIGN("404"),
    ANDROID("346"),
    KOTLIN("317"),
    MOBILE_DEV("281"),
    APP_DEV("274"),

    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard"),

    IS_PAID_TRUE("true"),
    IS_PAID_FALSE("false"),

}
