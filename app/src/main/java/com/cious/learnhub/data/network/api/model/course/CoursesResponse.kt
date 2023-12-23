package com.cious.learnhub.data.network.api.model.course


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CoursesResponse(
    @SerializedName("data")
    val data: List<CourseItemResponse>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)