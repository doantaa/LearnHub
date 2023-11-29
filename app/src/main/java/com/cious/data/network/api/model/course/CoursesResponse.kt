package com.cious.data.network.api.model.course


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CoursesResponse(
    @SerializedName("data")
    val data: List<CourseItemResponse?>,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
)