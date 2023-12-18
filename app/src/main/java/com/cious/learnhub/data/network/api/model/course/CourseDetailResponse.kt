package com.cious.learnhub.data.network.api.model.course

import com.google.gson.annotations.SerializedName

data class CourseDetailResponse(
    @SerializedName("data")
    val dataDetailResponse: CourseItemResponse,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)