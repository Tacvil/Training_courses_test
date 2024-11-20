package com.example.trainingCourses.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.example.trainingCourses.data.local.entity.CourseEntity
import com.example.trainingCourses.data.local.entity.FavoriteCourseEntity
import kotlinx.serialization.Serializable

@Serializable
data class Courses
@JvmOverloads
constructor(
    val id: Int? = null,
    val title: String? = null,
    val summary: String? = null,
    val full_name: String? = null,
    val display_price: String? = null,
    val cover: String? = null,
    val score: Double? = null,
    val create_date: String? = null,
    val time_to_complete: String? = null,
    val canonical_url: String? = null,
    val description: String? = null,
    val avatar: String? = null,
    var isFavorite: Boolean = false

) : Parcelable {
    override fun describeContents(): Int = 0

    override fun writeToParcel(
        parcel: Parcel,
        flags: Int
    ) {
        parcel.writeValue(id)
        parcel.writeString(title)
        parcel.writeString(summary)
        parcel.writeString(full_name)
        parcel.writeString(display_price)
        parcel.writeString(cover)
        parcel.writeValue(score)
        parcel.writeString(create_date)
        parcel.writeString(time_to_complete)
        parcel.writeString(canonical_url)
        parcel.writeString(description)
        parcel.writeString(avatar)
    }

    companion object CREATOR : Parcelable.Creator<Courses> {
        override fun createFromParcel(parcel: Parcel): Courses =
            Courses(
                id = parcel.readInt(),
                title = parcel.readString(),
                summary = parcel.readString(),
                full_name = parcel.readString(),
                display_price = parcel.readString(),
                cover = parcel.readString(),
                score = parcel.readDouble(),
                create_date = parcel.readString(),
                time_to_complete = parcel.readString(),
                canonical_url = parcel.readString(),
                description = parcel.readString(),
                avatar = parcel.readString()
            )

        override fun newArray(size: Int): Array<Courses?> = arrayOfNulls(size)
    }
}

