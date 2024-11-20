package com.example.trainingCourses.domain.utils

import com.example.trainingCourses.data.local.entity.CourseEntity
import com.example.trainingCourses.data.local.entity.FavoriteCourseEntity
import com.example.trainingCourses.domain.model.Courses

object CourseMappers {
    fun Courses.toCourseEntity(): CourseEntity {
        return CourseEntity(
            id_course = id!!,
            title = title,
            summary = summary,
            full_name = full_name,
            display_price = display_price,
            cover = cover,
            score = score,
            create_date = create_date,
            time_to_complete = time_to_complete,
            canonical_url = canonical_url,
            description = description,
            avatar = avatar,
            isFavorite = isFavorite
        )
    }

    fun CourseEntity.toCourses(): Courses {
        return Courses(
            id = this.id_course,
            title = this.title,
            summary = this.summary,
            full_name = this.full_name,
            display_price = this.display_price,
            cover = this.cover,
            score = this.score,
            create_date = this.create_date,
            time_to_complete = this.time_to_complete,
            canonical_url = this.canonical_url,
            description = this.description,
            avatar = this.avatar,
            isFavorite = this.isFavorite
        )
    }

    fun Courses.toFavoriteCourseEntity(): FavoriteCourseEntity {
        return FavoriteCourseEntity(
            id_course = id!!,
            title = title,
            summary = summary,
            full_name = full_name,
            display_price = display_price,
            cover = cover,
            score = score,
            create_date = create_date,
            time_to_complete = time_to_complete,
            canonical_url = canonical_url,
            description = description,
            avatar = avatar,
            isFavorite = true
        )
    }
    fun FavoriteCourseEntity.toCourses(): Courses {
        return Courses(
            id = this.id_course,
            title = this.title,
            summary = this.summary,
            full_name = this.full_name,
            display_price = this.display_price,
            cover = this.cover,
            score = this.score,
            create_date = this.create_date,
            time_to_complete = this.time_to_complete,
            canonical_url = this.canonical_url,
            description = this.description,
            avatar = this.avatar,
            isFavorite = this.isFavorite
        )
    }
}