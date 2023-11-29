package com.cious.data.network.api.model.course


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Course(
    @SerializedName("about")
    val about: String?,
    @SerializedName("categoryId")
    val categoryId: String?,
    @SerializedName("courseType")
    val courseType: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("instructor")
    val instructor: String?,
    @SerializedName("level")
    val level: String?,
    @SerializedName("moduleCount")
    val moduleCount: Int?,
    @SerializedName("objective")
    val objective: String?,
    @SerializedName("onboaring")
    val onboaring: String?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("telegramLink")
    val telegramLink: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("totalDuration")
    val totalDuration: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)